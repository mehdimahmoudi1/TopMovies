package com.example.topmovies.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.topmovies.R
import com.example.topmovies.databinding.ActorsItemsBinding
import com.example.topmovies.databinding.FragmentSearchBinding
import com.example.topmovies.db.database.MovieDatabase
import com.example.topmovies.repositories.search.SearchRepository
import com.example.topmovies.ui.search.adapter.SearchAdapter
import com.example.topmovies.utils.initRecycler
import com.example.topmovies.utils.showVisibility
import com.example.topmovies.viewmodels.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    //ViewModel
    val viewModel : SearchViewModel by viewModels()

    //Adapter
    @Inject
    lateinit var adapter : SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //search movies
            edtSearch.addTextChangedListener {
                val search = edtSearch.text.toString()
                if (search.isNotEmpty()){
                    viewModel.searchMovies(search)
                }
            }

            // get list
            viewModel.searchMovieLive.observe(viewLifecycleOwner){
                adapter.setData(it.data)
                searchRecycler.initRecycler(
                    LinearLayoutManager(requireContext()),
                    adapter
                )
            }
            //Loading
            viewModel.loading.observe(viewLifecycleOwner){
                if (it){
                    loading.showVisibility(true)
                }else{
                    loading.showVisibility(false)
                }
            }

            viewModel.empty.observe(viewLifecycleOwner){
                if (it){
                    emptyList.showVisibility(true)
                    searchRecycler.showVisibility(false)
                }else{
                    emptyList.showVisibility(false)
                    searchRecycler.showVisibility(true)
                }
            }

            adapter.setOnClockLister {
                val direction = SearchFragmentDirections.actionToDetail(it.id!!.toInt())
                findNavController().navigate(direction)
            }
        }
    }
}