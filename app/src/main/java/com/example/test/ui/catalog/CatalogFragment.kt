package com.example.test.ui.catalog

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.test.R
import com.example.test.databinding.FragmentDashboardBinding

class CatalogFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var editTextSearchQuery: EditText
    private lateinit var clearButton: Button
    private lateinit var clearHistoryButton: Button
    private lateinit var listView: ListView
    private lateinit var historyListView: ListView
    private lateinit var progressBar: ProgressBar
    private var searchQuery: String = ""

    private val PREFS_NAME = "search_prefs"
    private val SEARCH_HISTORY_KEY = "search_history"
    private lateinit var searchHistory: MutableList<String>
    private lateinit var historyAdapter: ArrayAdapter<String>
    private lateinit var categoryAdapter: CustomAdapter

    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        listView = binding.listView
        historyListView = root.findViewById(R.id.searchHistoryListView)
        editTextSearchQuery = root.findViewById(R.id.editTextText)
        clearButton = root.findViewById(R.id.clearButton)
        clearHistoryButton = root.findViewById(R.id.clearHistoryButton)
        progressBar = root.findViewById(R.id.progressBar)

        // Восстанавливаем состояние текста поискового запроса
        if (savedInstanceState != null) {
            searchQuery = savedInstanceState.getString("searchQuery", "")
            editTextSearchQuery.setText(searchQuery)
            clearButton.visibility = if (searchQuery.isNotEmpty()) View.VISIBLE else View.GONE
        }

        // Настраиваем адаптеры для списка категорий и истории поиска
        val dataList = listOf(
            Item(R.drawable.free_icon_appliances, "Бытовая техника"),
            Item(R.drawable.free_icon_hairdryer, "Здоровье и красота"),
            Item(R.drawable.free_icon_television, "Телевизоры"),
            Item(R.drawable.free_icon_smartphone, "Смартфоны и планшеты"),
            Item(R.drawable.free_icon_pc, "ПК и ноутбуки"),
            Item(R.drawable.free_icon_pc_tower, "Комплектующие для ПК"),
            Item(R.drawable.free_icon_ethernet, "Проводка и интернет"),
            Item(R.drawable.free_icon_tool_box, "Инструменты")
        )

        categoryAdapter = CustomAdapter(requireContext(), dataList)
        listView.adapter = categoryAdapter

        searchHistory = loadSearchHistory()
        historyAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, searchHistory)
        historyListView.adapter = historyAdapter

        // TextWatcher для строки поиска
        editTextSearchQuery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                toggleHistoryListView(s.isNullOrEmpty())

                // Сбрасываем и перезапускаем таймер для автоматического поиска
                searchRunnable?.let { handler.removeCallbacks(it) }
                searchRunnable = Runnable { performSearch() }
                handler.postDelayed(searchRunnable!!, 2000)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as Item
            editTextSearchQuery.setText(selectedItem.name)
            toggleHistoryListView(true) // Снова показываем категории после выбора истории
        }

        historyListView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            editTextSearchQuery.setText(selectedItem)
            toggleHistoryListView(true) // Снова показываем категории после выбора истории
        }

        clearButton.setOnClickListener {
            editTextSearchQuery.text.clear()
            clearButton.visibility = View.GONE
            hideKeyboard()
            toggleHistoryListView(true) // Возвращаем категории при очистке поиска
        }

        clearHistoryButton.setOnClickListener {
            clearSearchHistory()
            historyAdapter.notifyDataSetChanged()
        }

        return root
    }

    private fun toggleHistoryListView(showCategories: Boolean) {
        if (showCategories) {
            listView.visibility = View.VISIBLE
            historyListView.visibility = View.GONE
            clearHistoryButton.visibility = View.GONE
        } else {
            listView.visibility = View.GONE
            historyListView.visibility = View.VISIBLE
            clearHistoryButton.visibility = View.VISIBLE
        }
    }

    private fun performSearch() {
        val query = editTextSearchQuery.text.toString()
        if (query.isNotEmpty()) {
            saveSearchQuery(query)
            hideKeyboard()
            toggleHistoryListView(true)
            showProgressBar(true)
            // Здесь выполняется код поиска
            Handler(Looper.getMainLooper()).postDelayed({
                showProgressBar(false)
            }, 3000) // Имитация времени выполнения поиска (3 секунды)
        }
    }

    private fun showProgressBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("searchQuery", editTextSearchQuery.text.toString())
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun saveSearchQuery(query: String) {
        if (query.isNotEmpty() && !searchHistory.contains(query)) {
            if (searchHistory.size >= 10) {
                searchHistory.removeAt(0)
            }
            searchHistory.add(query)
            saveSearchHistory()
            historyAdapter.notifyDataSetChanged()
        }
    }

    private fun loadSearchHistory(): MutableList<String> {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val historySet = prefs.getStringSet(SEARCH_HISTORY_KEY, setOf()) ?: setOf()
        return historySet.toMutableList()
    }

    private fun saveSearchHistory() {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putStringSet(SEARCH_HISTORY_KEY, searchHistory.toSet())
        editor.apply()
    }

    private fun clearSearchHistory() {
        searchHistory.clear()
        saveSearchHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // Удаляем все колбэки из хендлера при уничтожении фрагмента
        handler.removeCallbacksAndMessages(null)
    }
}
