package com.example.test.ui.catalog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.test.R
import com.example.test.databinding.FragmentDashboardBinding

class CatalogFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!

    private lateinit var editTextSearchQuery: EditText
    private lateinit var clearButton: Button
    private var searchQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var listView = binding.listView
        editTextSearchQuery = root.findViewById(R.id.editTextText)
        clearButton = root.findViewById(R.id.clearButton)


        // Восстанавливаем состояние текста поискового запроса
        if (savedInstanceState != null) {
            searchQuery = savedInstanceState.getString("searchQuery", "")
            editTextSearchQuery.setText(searchQuery)
            clearButton.visibility = if (searchQuery.isNotEmpty()) View.VISIBLE else View.GONE
        }

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

        val adapter = CustomAdapter(requireContext(), dataList)
        listView.adapter = adapter

        // TextWatcher for search query EditText
        editTextSearchQuery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        clearButton.setOnClickListener {
            editTextSearchQuery.text.clear()
            clearButton.visibility = View.GONE
            hideKeyboard()
        }

        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("searchQuery", editTextSearchQuery.text.toString())
    }

    private fun hideKeyboard() {
        val imm = getSystemService(requireContext(), InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}