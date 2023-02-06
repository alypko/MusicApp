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
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)



//        fun bind(item: Song)  {
//            itemBinding.tvPrimary.text = item.title
//            itemBinding.tvSecondary.text = item.subtitle
//            Glide.with(itemView)
//                .load(item.imageUrl)
//                .into(itemBinding.ivItemImage)
//            itemBinding.root.setOnClickListener {
//                onItemClickListener?.let {click ->
//                    click(item)
//                }
//            }
//        }
//    }

    private val diffCallback = object : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.mediaId == newItem.mediaId
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var songs: List<Song>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

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

    private var onItemClickListener: ((Song) -> Unit)? = null

    fun setOnItemClickListener(listener: (Song) -> Unit){
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return songs.size
    }
}














