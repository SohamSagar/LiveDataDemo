package com.example.livedatademo.adapter

import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.livedatademo.MainActivity
import com.example.livedatademo.R
import com.example.livedatademo.`interface`.MyOnClick
import com.example.livedatademo.model.MyData

class MyAdapter(
    val context: MainActivity,
    var data: MutableLiveData<ArrayList<MyData>>,
    val onClickListener: MyOnClick
): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_adapter_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data!!.value!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context)
            .load(data!!.value!!.get(position).imgae)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    // Hide the ProgressBar when image loading fails
                    holder.itemView.findViewById<ProgressBar>(R.id.pb).visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    // Hide the ProgressBar and show the image when it's loaded successfully
                    holder.itemView.findViewById<ProgressBar>(R.id.pb).visibility = View.GONE
                    return false
                }
            })
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.itemView.findViewById(R.id.img))


        holder.itemView.findViewById<TextView>(R.id.tv).text=data!!.value!!.get(position).name

        holder.itemView.setOnLongClickListener {
            onClickListener.onClick(position)
            true
        }
    }
}