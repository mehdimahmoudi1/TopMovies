package com.example.topmovies.utils

import android.view.View
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView

fun View.showVisibility(isShown:Boolean){
    if (isShown){
        this.visibility = View.VISIBLE
    }else{
        this.visibility = View.GONE
    }
}

fun RecyclerView.initRecycler(
    layouManager: RecyclerView.LayoutManager,
    adapter: RecyclerView.Adapter<*>){
    this.adapter = adapter
    this.layoutManager = layouManager
}