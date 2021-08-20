package com.github.showitemlist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.showitemlist.R
import com.github.showitemlist.databinding.ItemBinding
import com.github.showitemlist.viewmodel.ItemListViewModel

class ListItemViewAdapter(private val context:Context,private var listItemModels:ArrayList<ItemListViewModel>):
    RecyclerView.Adapter<ListItemViewAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val itemBinding: ItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(itemListViewModel: ItemListViewModel){
            this.itemBinding.itemListViewModel=itemListViewModel
            itemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var itemBinding:ItemBinding=DataBindingUtil.inflate(layoutInflater, R.layout.items,parent,false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var itemViewModel=listItemModels[position]
        holder.bind(itemViewModel)
    }

    override fun getItemCount(): Int {
       return listItemModels.size
    }
}