package com.example.topmovies.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.topmovies.databinding.GenersItemBinding
import com.example.topmovies.databinding.TopMoviesItemBinding
import com.example.topmovies.models.ResponseGenres
import com.example.topmovies.models.topmovie.ResponseTopMovies
import javax.inject.Inject

class GenresAdapter @Inject constructor() : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

    private lateinit var binding: GenersItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenresAdapter.ViewHolder {
        binding = GenersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: GenresAdapter.ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        //don,t show Duplicate item
        holder.setIsRecyclable(true)
    }

    override fun getItemCount() = differ.currentList.size

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setData(item: ResponseGenres.ResponseGenersItem) {
            binding.apply {
                genersItem.text = item.name
            }
        }
    }

    //Differ CallBack
    private val differCallBack =
        object : DiffUtil.ItemCallback<ResponseGenres.ResponseGenersItem>() {
            override fun areItemsTheSame(
                oldItem: ResponseGenres.ResponseGenersItem,
                newItem: ResponseGenres.ResponseGenersItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ResponseGenres.ResponseGenersItem,
                newItem: ResponseGenres.ResponseGenersItem
            ): Boolean {
                return oldItem == newItem
            }

        }

    //differ
    val differ = AsyncListDiffer(this, differCallBack)
}