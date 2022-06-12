package com.example.topmovies.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.topmovies.databinding.TopMoviesItemBinding
import com.example.topmovies.models.topmovie.ResponseTopMovies
import com.example.topmovies.models.topmovie.ResponseTopMovies.Data
import javax.inject.Inject

class TopMoviesAdapter @Inject constructor() : RecyclerView.Adapter<TopMoviesAdapter.ViewHolder>() {

    private lateinit var binding: TopMoviesItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopMoviesAdapter.ViewHolder {
        binding = TopMoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: TopMoviesAdapter.ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        //don,t show Duplicate item
        holder.setIsRecyclable(true)
    }

    override fun getItemCount() = if (differ.currentList.size > 7) 5 else differ.currentList.size

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setData(item: Data) {
            binding.apply {
                title.text = item.title
                detail.text = "${item.imdbRating} | ${item.country} | ${item.year}"
                posterImg.load(item.poster) {
                    crossfade(true)
                    crossfade(1000)
                }
                binding.root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }
    //Set OnClick
    private var onItemClickListener : ((Data)->Unit)? = null

    fun setOnClickListener(listener: (Data)->Unit){
        onItemClickListener = listener
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)
}