package com.example.test.ui.like

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.FragmentLikesBinding


class LikesFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<LikesAdapter.ViewHolder>? = null

    private var _binding: FragmentLikesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLikesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        layoutManager = LinearLayoutManager(requireContext())
        adapter = LikesAdapter()
        binding.ResViewLikes.layoutManager = layoutManager
        binding.ResViewLikes.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}