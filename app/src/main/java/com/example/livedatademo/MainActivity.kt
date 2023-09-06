package com.example.livedatademo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.livedatademo.adapter.MyAdapter
import com.example.livedatademo.model.MyData
import com.example.livedatademo.viewModel.MyViewModel

class MainActivity : AppCompatActivity() {

    private var data: MutableLiveData<ArrayList<MyData>> = MutableLiveData()
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        //Getdata from viewmodel
        viewModel.getData().observe(this) {
            // update UI
            data!!.value = it
            Log.e("MainActivity ", "Data Send12")
            adapter = MyAdapter(this,data,viewModel.onClickListener)
            findViewById<TextView>(R.id.tvNodataFound).isVisible = it.size==0
            findViewById<RecyclerView>(R.id.rv).layoutManager = LinearLayoutManager(this)
            findViewById<RecyclerView>(R.id.rv).adapter=adapter
        }

        //Change swipe refresh arrow icon color
        findViewById<SwipeRefreshLayout>(R.id.rvSwipe).setColorSchemeColors(resources.getColor(R.color.blue))

        //Reload data
        findViewById<SwipeRefreshLayout>(R.id.rvSwipe).setOnRefreshListener {
            // on below line we are setting is refreshing to false.
            viewModel.loadData(findViewById(R.id.rvSwipe))
        }
    }
}