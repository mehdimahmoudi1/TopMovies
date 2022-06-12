package com.example.topmovies.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.topmovies.databinding.ActorsItemsBinding
import com.example.topmovies.databinding.MoviesListItemsBinding
import com.example.topmovies.models.detail.ResponseDetailMovies
import com.example.topmovies.models.topmovie.ResponseTopMovies
import com.example.topmovies.models.topmovie.ResponseTopMovies.Data
import com.example.topmovies.ui.home.adapters.TopMoviesAdapter
import javax.inject.Inject

class ActorsAdapter @Inject constructor() : RecyclerView.Adapter<ActorsAdapter.ViewHolder>() {

    private lateinit var binding: ActorsItemsBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsAdapter.ViewHolder {
        binding = ActorsItemsBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ActorsAdapter.ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root){
        fun setData(item : String){
            binding.apply {
                imgActors.load(item){
                    crossfade(true)
                    crossfade(1000)
                }
            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)
}