package lyka.aot.aotwallpaper.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import lyka.aot.aotwallpaper.Activities.WallpaperActivity
import lyka.aot.aotwallpaper.Entity.Wallpaper
import lyka.aot.aotwallpaper.Entity.FavouriteEntity
import lyka.aot.aotwallpaper.R
import lyka.aot.aotwallpaper.Repository.FavouriteRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random
import lyka.aot.aotwallpaper.Activities.WallpaperListActivity

class CategoryAdapter(
  private var datalist: MutableList<Wallpaper>,
  private val context: Context,
  private val recyclerView: RecyclerView,
  private val trending: Boolean = false,
  private val activity: Activity? = null,
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
    datalist.addAll(newdata)
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
    val loadingImage: ImageView = itemview.findViewById(R.id.loadingimage)


  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {

    var widthi = calculateItemWidth()
    var heighti = widthi * 2


    var itemview: View

    if (trending) {
      itemview =
        LayoutInflater.from(parent.context).inflate(R.layout.trending_itemview, parent, false)

    } else {
      itemview =
        LayoutInflater.from(parent.context).inflate(R.layout.wallpaper_itemview, parent, false)

    }
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
    val colorDrawable = ColorDrawable(generateRandomColor())
    holder.loadingImage.background = colorDrawable

    removeFavourite(holder)

    var isFavourite = favouriteList.any {
      it.id == datalist[position].id

    }

    if (isFavourite) {
      addFavourite(holder)
    }

    if (activity is WallpaperListActivity) {
      activity.hideProgressbar()
    }

    Glide.with(context)
      .load(datalist[position].imageUrl)
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
      .placeholder(R.drawable.baseline_favorite_24)
      .error(R.drawable.baseline_favorite_24)
      .into(holder.favoutite)
  }

  private fun removeFavourite(holder: Myviewholder) {
    Glide.with(context)
      .load(R.drawable.border_favourite)
      .placeholder(R.drawable.border_favourite)
      .error(R.drawable.border_favourite)
      .into(holder.favoutite)

  }

  private fun generateRandomColor(): Int {
    val r = Random.nextInt(256)
    val g = Random.nextInt(256)
    val b = Random.nextInt(256)
    return Color.rgb(r, g, b)
  }
}