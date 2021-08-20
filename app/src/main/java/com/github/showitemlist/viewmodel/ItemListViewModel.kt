package com.github.showitemlist.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.github.showitemlist.adapter.ListItemViewAdapter
import com.github.showitemlist.api.ApiClient
import com.github.showitemlist.api.ApiRequest
import com.github.showitemlist.api.ServerCallback
import com.github.showitemlist.model.Item
import com.github.showitemlist.util.CustomLoadingPb
import io.reactivex.Observable
import java.util.ArrayList

class ItemListViewModel : ViewModel {
    var title: String? = null
    var description: String? = null
    var itemResponseList: List<Item>? = null
    private var itemList: Array<Item> = arrayOf()

    constructor() : super()
    constructor(item: Item) : super() {
        this.title = item.name
        this.description = item.description
    }

    var modelDataArrayList = ArrayList<ItemListViewModel>()

    // function for call Api and setItems in  View
    fun setItems(
        context: Context,
        recyclerView: RecyclerView
    ) {
        val customLoadingPb = CustomLoadingPb(context)
        customLoadingPb.show()
        try {
            val observable: Observable<List<Item?>>? =
                ApiClient.getApiInterface(context!!)?.getRepositoryDetails()
            val apiRequest = ApiRequest()
            apiRequest.requestApi(context!!, listOf(observable), object : ServerCallback {
                override fun onSuccess(obj: Any?) {
                    if (obj != null) {
                        try {
                            itemResponseList = obj as List<Item>?
                        } catch (e: java.lang.Exception) {
                            Log.d("Error", e.toString())
                        }
                    } else {
                        Log.d("rxjava", "response null")
                    }
                }

                override fun onTokenExpiry() {
                    try {

                    } catch (e: Exception) {
                        Log.d("Error", e.toString())
                    }
                }

                override fun onError(throwable: Throwable?) {
                    Log.d("rxjava", "onFailure: " + throwable?.message)
                    customLoadingPb.dismiss()
                }

                override fun onNetworkError() {
                    Log.d("rxjava", "onNetworkError")
                    customLoadingPb.dismiss()
                }

                override fun onComplete() {
                    for (listItems in itemResponseList!!) {
                        var item = Item(listItems.name ?: "", listItems.description ?: "")
                        var ivm: ItemListViewModel = ItemListViewModel(item)
                        modelDataArrayList!!.add(ivm)
                    }

                    var listItemViewAdapter =
                        ListItemViewAdapter(context, modelDataArrayList!!)
                    recyclerView!!.adapter = listItemViewAdapter
                    customLoadingPb.dismiss()
                }
            })

        } catch (e: Exception) {
            Log.d("Error", e.toString() + "")
        }
    }
}