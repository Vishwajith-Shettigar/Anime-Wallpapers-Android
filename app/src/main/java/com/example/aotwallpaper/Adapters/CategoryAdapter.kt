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

class CategoryAdapter(private val context: Context, private val data: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.Myviewholder>() {

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

        val imageUrl = ""
//        Glide.with(context)
//            .load(R.drawable.friends)
////             .placeholder(R.drawable.placeholder)
////             .error(R.drawable.error)
//            .into(holder.imageView)
//
//        val currentItem = data[position]
//
        // Assuming you have a drawable resource ID
        var drawableId=R.drawable.friends
        if(data[position].cat_id==2)
          drawableId = R.drawable.chibi
        if(data[position].cat_id==1)
          drawableId = R.drawable.romace


        // Get the drawable from the resource ID
        val drawable = ContextCompat.getDrawable(holder.itemView.context, drawableId)

        // Set the drawable to the ImageView
        holder.imageView.setImageDrawable(drawable)


    }
}