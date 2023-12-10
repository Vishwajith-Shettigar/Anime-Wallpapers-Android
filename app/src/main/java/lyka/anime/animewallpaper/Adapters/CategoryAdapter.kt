package lyka.aot.animewallpaper.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lyka.aot.animewallpaper.Activities.WallpaperActivity
import lyka.aot.animewallpaper.Entity.Wallpaper
import lyka.aot.animewallpaper.Entity.FavouriteEntity
import lyka.aot.animewallpaper.R
import lyka.aot.animewallpaper.Repository.FavouriteRepository
import kotlinx.coroutines.GlobalScope
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


    private suspend fun loadFavourites() {
        favouriteList = favouriteRepository.getAllFavourites()
    }

    suspend fun setdata(newdata: MutableList<Wallpaper>) {

        loadFavourites()
        newdata.shuffle()
        datalist = newdata
        notifyDataSetChanged()

    }

    suspend fun notifyFavouriteChanges(){
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

        removeFavourite(holder)

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
            intent.putExtra("id", datalist[position].id)
            (context as Activity).startActivityForResult(intent, 1001)
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
            .load(R.drawable.border_favourite)
            .placeholder(R.drawable.cornerradius)
            .error(R.drawable.cornerradius)
            .into(holder.favoutite)

    }

}