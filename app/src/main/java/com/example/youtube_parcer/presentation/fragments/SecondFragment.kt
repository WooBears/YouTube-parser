package com.example.youtube_parcer.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.youtube_parcer.R
import com.example.youtube_parcer.databinding.FragmentSecondBinding
import com.example.youtube_parcer.presentation.adapter.VideoAdapter
import com.example.youtube_parcer.presentation.viewmodel.VideoViewModel
import com.example.youtube_parcer.util.Resource


class SecondFragment : Fragment() {


    private lateinit var binding: FragmentSecondBinding
    private val args: SecondFragmentArgs by navArgs()
    private lateinit var adapter: VideoAdapter
    private val viewModel: VideoViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = VideoAdapter()
        binding.rvRecycler.adapter = adapter
        val getId = args.id
        binding.tvVideoDescription.text = args.title

        viewModel.getPlayListVideo(getId).observe(viewLifecycleOwner) {state ->

            when(state){
                is Resource.Error -> {
                    Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    //progressbarDoIt
                }
                is Resource.Success -> {
                   val item = state.data
                   adapter.submitList(listOf(item))
                }
            }
        }
    }
}