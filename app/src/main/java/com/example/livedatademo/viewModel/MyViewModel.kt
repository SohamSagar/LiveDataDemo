package com.example.livedatademo.viewModel

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.livedatademo.R
import com.example.livedatademo.`interface`.MyOnClick
import com.example.livedatademo.model.MyData

open class MyViewModel:ViewModel() {
    private var data:MutableLiveData<ArrayList<MyData>>? = null

    //Remove item click receiver
    val onClickListener = object : MyOnClick {
        override fun onClick(position: Int) {
            val currentList = data!!.value ?: return
            if (position in 0 until currentList.size) {
                currentList.removeAt(position)
                data!!.value = currentList
            }
        }
    }

    //Get data
    internal fun getData():MutableLiveData<ArrayList<MyData>>{
        if (data == null) {
            data = MutableLiveData()
            loadData(null)
        }
        return data as MutableLiveData<ArrayList<MyData>>
    }

    //Load data
    fun loadData(swipeRefreshLayout: SwipeRefreshLayout?) {
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            val tempData = ArrayList<MyData>()
            tempData.add(MyData("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops","https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"))
            tempData.add(MyData("Mens Casual Premium Slim Fit T-Shirts ","https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg"))
            tempData.add(MyData("Mens Cotton Jacket","https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg"))
            tempData.add(MyData("Mens Casual Slim Fit","https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg"))
            tempData.add(MyData("John Hardy Women's Legends Naga Gold & Silver Dragon Station Chain Bracelet","https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg"))
            tempData.add(MyData("Solid Gold Petite Micropave ","https://fakestoreapi.com/img/61sbMiUnoGL._AC_UL640_QL65_ML3_.jpg"))
            data!!.postValue(tempData)
            if (swipeRefreshLayout!=null)
                swipeRefreshLayout.isRefreshing = false
        }, 2000)
    }
}