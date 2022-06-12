package com.example.topmovies.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.topmovies.databinding.MoviesListItemsBinding
import com.example.topmovies.models.lastmovies.ResponseLastMovies.Data
import javax.inject.Inject

class SearchAdapter @Inject constructor() :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private lateinit var binding: MoviesListItemsBinding
    var movieList = emptyList<Data>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        binding = MoviesListItemsBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        holder.bindData(movieList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = movieList.size


    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Data) {
            binding.apply {
                title.text = item.title.toString()
                txtCountry.text = item.country.toString()
                txtRate.text = item.imdbRating.toString()
                txtYear.text = item.year.toString()
                imgPoster.load(item.poster) {
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

    //Set On Click
    private var onItemClickListener: ((Data) -> Unit)? = null

    //Set listener
    fun setOnClockLister(listener: ((Data) -> Unit)?) {
        onItemClickListener = listener
    }

    //set data with differ call back
    fun setData(data: List<Data>) {
        val movieDiffUtil = MovieDiffUtils(movieList, data)
        val diffUtil = DiffUtil.calculateDiff(movieDiffUtil)
        movieList = data
        diffUtil.dispatchUpdatesTo(this)
    }

    class MovieDiffUtils(private val oldItem: List<Data>, private val newItem: List<Data>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

    }
}