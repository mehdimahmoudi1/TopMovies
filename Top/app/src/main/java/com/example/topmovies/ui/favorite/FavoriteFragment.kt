package com.example.topmovies.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topmovies.databinding.FragmentFavoriteBinding
import com.example.topmovies.ui.favorite.adapter.FavoriteAdapter
import com.example.topmovies.ui.search.SearchFragmentDirections
import com.example.topmovies.utils.initRecycler
import com.example.topmovies.utils.showVisibility
import com.example.topmovies.viewmodels.favorite.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding : FragmentFavoriteBinding

    //ViewModel
    private val viewModel : FavoriteViewModel by viewModels()
    //Adapter
    @Inject
    lateinit var adapter : FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.getAllFavoriteList()
            viewModel.favoriteListLive.observe(viewLifecycleOwner){
               adapter.setData(it)
            }
            favoriteRecycler.initRecycler(
                LinearLayoutManager(requireContext()),
                adapter
            )

            //Empty List
            viewModel.empty.observe(viewLifecycleOwner){
                if (it){
                    emptyListLay.showVisibility(true)
                    favoriteRecycler.showVisibility(false)
                }else{
                    emptyListLay.showVisibility(false)
                    favoriteRecycler.showVisibility(true)
                }
            }
            adapter.setOnClickListener {
                val direction = FavoriteFragmentDirections.actionToDetail(it.id!!.toInt())
                findNavController().navigate(direction)
            }

        }
    }
}