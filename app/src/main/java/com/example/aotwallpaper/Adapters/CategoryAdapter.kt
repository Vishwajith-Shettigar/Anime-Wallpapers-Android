package com.example.aotwallpaper.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aotwallpaper.Data.Category
import com.example.aotwallpaper.R

class CategoryAdapter(private val context: Context, private var data: MutableList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.Myviewholder>() {

    fun setdata(newdata: MutableList<Category>) {
        data = newdata
        notifyDataSetChanged()

    }

    public class Myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.categoryImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {

        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.category_itemv_iew, parent, false)
        return Myviewholder(itemview)
    }

    override fun getItemCount(): Int {

        return data.size
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {

        Glide.with(context)
            .load(data[position].imageUrl)
            .placeholder(R.drawable.cornerradius)
//             .error(R.drawable.error)
            .into(holder.imageView)
    }
}