package com.example.musicapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.R
import com.example.musicapp.adapters.SongAdapter
import com.example.musicapp.databinding.ListItemBinding
import com.example.musicapp.other.Status
import com.example.musicapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var songAdapter: SongAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setupRecyclerView()
        subscribeToObservers()


        songAdapter.setItemClickListener {
            mainViewModel.playOrToggleSong(it)
        }

    }

    private fun setupRecyclerView() = rvAllSongs.apply {
        adapter = songAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun subscribeToObservers() {
        mainViewModel.mediaItems.observe(viewLifecycleOwner) { result ->
            when(result.status) {
                Status.SUCCESS -> {
                    allSongsProgressBar.isVisible = false
                    result.data?.let {songs ->
                        songAdapter.songs = songs
                    }
                }
                Status.ERROR -> Unit
                Status.LOADING ->  allSongsProgressBar.isVisible = true
            }
        }
    }
}