package com.example.youtube_parcer.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.youtube_parcer.R
import com.example.youtube_parcer.databinding.FragmentFirstBinding
import com.example.youtube_parcer.model.Channel
import com.example.youtube_parcer.presentation.adapter.PlaylistAdapter
import com.example.youtube_parcer.presentation.viewmodel.PlaylistViewModel
import com.example.youtube_parcer.util.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel


class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private val viewModel: PlaylistViewModel by viewModel()
    private lateinit var adapter: PlaylistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PlaylistAdapter(this::onClick)

        viewModel.getPlayList().observe(viewLifecycleOwner){state ->

            when(state){
                is Resource.Error -> {
                    Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    //progressbarDoIt
                }
                is Resource.Success -> {
                    val item = state.data?.items
                    item?.let { adapter.submitList(it) }
                }
            }
            binding.recycler.adapter = adapter
        }

    }

    private fun onClick(item: Channel.Item){

        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(
            item.id,
            item.snippet.title
        )

        findNavController().navigate(action)
    }
}