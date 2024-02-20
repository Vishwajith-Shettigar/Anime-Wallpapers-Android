package lyka.aot.animewallpapers.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import lyka.aot.animewallpapers.Activities.WallpaperActivity
import lyka.aot.animewallpapers.Entity.FavouriteEntity
import lyka.aot.animewallpapers.R
import lyka.aot.animewallpapers.Repository.FavouriteRepository
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

        notifyDataSetChanged()

    }

    suspend fun notifyFavouriteChanges() {
        loadFavourites()
        notifyDataSetChanged()
    }


    class Myviewholder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val parent: RelativeLayout = itemview.findViewById(R.id.image_layout)
        val image: ImageView = itemview.findViewById(R.id.image)
        val favoutite: ImageView = itemview.findViewById(R.id.favourite_image)
        val loadindLayout: RelativeLayout = itemview.findViewById(R.id.loading_layout)


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
            .error(R.drawable.cornerradius)
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    holder.loadindLayout.visibility = View.GONE
                    holder.parent.visibility = View.VISIBLE
                    return false
                }
            })
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