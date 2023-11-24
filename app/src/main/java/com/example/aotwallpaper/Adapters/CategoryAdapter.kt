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
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aotwallpaper.Activities.WallpaperActivity
import com.example.aotwallpaper.AotApplication
import com.example.aotwallpaper.Data.Category
import com.example.aotwallpaper.Data.Wallpaper
import com.example.aotwallpaper.Entity.FavouriteEntity
import com.example.aotwallpaper.R
import com.example.aotwallpaper.Repository.FavouriteRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryAdapter(
    private var datalist: MutableList<Wallpaper>,
    private val context: Context,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<CategoryAdapter.Myviewholder>() {

    private var favouriteList = mutableListOf<FavouriteEntity>()

    @Inject
    lateinit var favouriteRepository: FavouriteRepository

    private var currentPosition: Int = RecyclerView.NO_POSITION
    fun setdata(newdata: MutableList<Wallpaper>) {
        GlobalScope.launch {

            favouriteList = favouriteRepository.getAllFavourites()
        }
        datalist = newdata
        notifyDataSetChanged()

    }

    fun updateAdapter() {
        Log.e("#","update adapter")
        // Update your adapter's data or perform any necessary actions
        // In your case, you might want to refresh the favouriteList based on the modified data
        MainScope().launch {
            favouriteList = favouriteRepository.getAllFavourites()
            notifyDataSetChanged()

            // Scroll to the saved position
//            if (currentPosition != RecyclerView.NO_POSITION) {
//                recyclerView.scrollToPosition(currentPosition)
//            }
        }
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


        var isFavourite = favouriteList.any {
            it.id == datalist[position].id

        }

        if (isFavourite) {
            addFavourite(holder)
        }

        Glide.with(context)
            .load(datalist[position].imageUrl)
            .placeholder(R.drawable.cornerradius)
            .error(R.drawable.cornerradius)
            .into(holder.image)

        holder.image.setOnClickListener {
            val intent = Intent(context, WallpaperActivity::class.java)
            intent.putExtra("imageUrl", datalist[position].imageUrl)
            intent.putExtra("isFavourite", isFavourite)
            intent.putExtra("id",  datalist[position].id)
            (context as Activity).startActivityForResult(intent,1001)
        }


        holder.favoutite.setOnClickListener {
            isFavourite = favouriteList.any {
                it.id == datalist[position].id

            }


            if (!isFavourite) {
                isFavourite = true
                val favouriteEntity =
                    FavouriteEntity(datalist[position].id, datalist[position].imageUrl)
                GlobalScope.launch {
                    favouriteRepository.insert(favouriteEntity)
                }
                favouriteList.add(favouriteEntity)

                addFavourite(holder)

            } else {
                isFavourite = false
                GlobalScope.launch {
                    favouriteRepository.deleteFavourite(datalist[position].id)
                }

                favouriteList.removeIf {
                    it.id == datalist[position].id

                }

                removeFavourite(holder)
            }
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
            .load(R.drawable.baseline_favorite_border_24)
            .placeholder(R.drawable.cornerradius)
            .error(R.drawable.cornerradius)
            .into(holder.favoutite)

    }

}