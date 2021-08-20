package com.github.showitemlist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.showitemlist.R
import com.github.showitemlist.adapter.ListItemViewAdapter
import com.github.showitemlist.viewmodel.ItemListViewModel

class ItemListMainActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
     private var viewManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_item_main)
        recyclerView = findViewById(R.id.rv_main)
        var itemListViewModel:ItemListViewModel=ItemListViewModel()
        recyclerView!!.layoutManager = viewManager
        itemListViewModel.setItems(this,recyclerView!!)
    }

}