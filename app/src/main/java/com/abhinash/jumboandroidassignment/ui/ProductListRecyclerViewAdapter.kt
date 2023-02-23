package com.abhinash.jumboandroidassignment.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.abhinash.jumboandroidassignment.databinding.FragmentProductListItemBinding


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ProductListRecyclerViewAdapter(
) : RecyclerView.Adapter<ProductListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentProductListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = null

    }

    override fun getItemCount(): Int = 0

    inner class ViewHolder(binding: FragmentProductListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

}