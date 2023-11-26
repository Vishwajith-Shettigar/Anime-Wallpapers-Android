package com.example.aotwallpaper.Adapters

import android.app.Activity
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
import com.example.aotwallpaper.Entity.Wallpaper
import com.example.aotwallpaper.Entity.FavouriteEntity
import com.example.aotwallpaper.R
import com.example.aotwallpaper.Repository.FavouriteRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteAdapter(
    private var datalist: MutableList<FavouriteEntity>,
    private val context: Context,
) : RecyclerView.Adapter<FavouriteAdapter.Myviewholder>() {

    @Inject
    lateinit var favouriteRepository: FavouriteRepository

    private suspend fun loadFavourites() {
        datalist = favouriteRepository.getAllFavourites()
    }

    fun setdata(newdata: MutableList<FavouriteEntity>) {


        datalist = newdata
        Log.e("#", "adapter " + datalist.size)
        notifyDataSetChanged()

    }

    suspend fun notifyFavouriteChanges() {
        loadFavourites()
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


        holder.favoutite.visibility = View.GONE

        Glide.with(context)
            .load(datalist[position].imageurl)
            .placeholder(R.drawable.cornerradius)
            .error(R.drawable.cornerradius)
            .into(holder.image)

        holder.image.setOnClickListener {
            val intent = Intent(context, WallpaperActivity::class.java)
            intent.putExtra("imageUrl", datalist[position].imageurl)
            intent.putExtra("isFavourite", true)
            intent.putExtra("id", datalist[position].id)
            (context as Activity).startActivityForResult(intent, 1001)
        }


    }

    private fun calculateItemWidth(): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        return (screenWidth / 2) - 20
    }

    private fun addFavourite(holder: Myviewholder) {

        Glide.with(context)
            .load(R.drawable.baseline_favorite_24)
            .placeholder(R.drawable.cornerradius)
            .error(R.drawable.cornerradius)
            .into(holder.favoutite)
    }

    private fun removeFavourite(holder: Myviewholder) {
        Glide.with(context)
            .load(R.drawable.border_favourite)
            .placeholder(R.drawable.cornerradius)
            .error(R.drawable.cornerradius)
            .into(holder.favoutite)

    }

}