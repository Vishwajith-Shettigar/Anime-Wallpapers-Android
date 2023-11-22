package com.example.aotwallpaper.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aotwallpaper.Activities.WallpaperActivity
import com.example.aotwallpaper.Data.Category
import com.example.aotwallpaper.Data.Wallpaper
import com.example.aotwallpaper.R
import kotlinx.coroutines.NonDisposableHandle.parent

class CategoryAdapter(
    private var datalist: MutableList<Wallpaper>,
    private val context: Context
) : RecyclerView.Adapter<CategoryAdapter.Myviewholder>() {

    fun setdata(newdata: MutableList<Wallpaper>) {
        datalist = newdata
        notifyDataSetChanged()

    }

    class Myviewholder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val parent: RelativeLayout = itemview.findViewById(R.id.parent)
        val image: ImageView = itemview.findViewById(R.id.image)
        val favoutite: ImageView = itemview.findViewById(R.id.favourite_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        var widthi = calculateItemWidth()
        var heighti = widthi * 2
        var itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.wallpaper_itemview, parent, false)
        itemview.layoutParams.apply {
            width = widthi
            height = heighti
        }

        return Myviewholder(itemview)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {


        Glide.with(context)
            .load(datalist[position].imageUrl)
            .placeholder(R.drawable.cornerradius)
            .error(R.drawable.cornerradius)
            .into(holder.image)

        holder.parent.setOnClickListener {
            val intent=Intent(context,WallpaperActivity::class.java)
            intent.putExtra("imageUrl",datalist[position].imageUrl)
            intent.putExtra("isFavourite",false)
            context.startActivity(intent)
        }
    }

    private fun calculateItemWidth(): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        return (screenWidth / 2) - 20
    }

}