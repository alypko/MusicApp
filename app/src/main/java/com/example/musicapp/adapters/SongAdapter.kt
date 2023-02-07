package com.example.musicapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.musicapp.R
import com.example.musicapp.data.entities.Song
import com.example.musicapp.databinding.FragmentSongBinding
import com.example.musicapp.databinding.ListItemBinding
import kotlinx.android.synthetic.main.list_item.view.*
import javax.inject.Inject


class SongAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseSongAdapter(R.layout.list_item) {


    override val differ = AsyncListDiffer(this, diffCallback)
    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]

        holder.itemView.apply {
            tvSecondary.text = song.subtitle
            tvPrimary.text = song.title

            glide.load(song.imageUrl).into(ivItemImage)

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(song)
                }
            }
        }
    }

}














