package lyka.aot.animewallpapers.Adapters

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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import lyka.aot.animewallpapers.Activities.WallpaperListActivity
import lyka.aot.animewallpapers.Data.Category
import lyka.aot.animewallpapers.R

import com.bumptech.glide.load.DataSource

import com.bumptech.glide.request.target.Target
import kotlin.random.Random


class HomeCategoryAdapter(private val context: Context, private var data: MutableList<Category>) :
    RecyclerView.Adapter<HomeCategoryAdapter.Myviewholder>() {

    fun setdata(newdata: MutableList<Category>) {
        data = newdata
        notifyDataSetChanged()

    }

    class Myviewholder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val parent: RelativeLayout = itemView.findViewById(R.id.image_layout)

        val imageView: ImageView = itemview.findViewById(R.id.image)
        val favoutite: ImageView = itemview.findViewById(R.id.favourite_image)
        val loading: RelativeLayout = itemview.findViewById(R.id.loading_layout)
      val loadingImage:ImageView=itemview.findViewById(R.id.loadingimage)

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

        return data.size
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {

        holder.favoutite.visibility=View.GONE
      val colorDrawable = ColorDrawable(generateRandomColor())
      holder.loadingImage.background=colorDrawable



        Glide.with(context)
            .load(data[position].imageUrl)
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
                    holder.loading.visibility = View.GONE
                    holder.parent.visibility = View.VISIBLE
                    return false
                }
            })
            .into(holder.imageView)

        holder.imageView.setOnClickListener {
            var intent = Intent(context, WallpaperListActivity::class.java)
            intent.putExtra("cat_id", data[position].cat_id)
            intent.putExtra("cat_name", data[position].name)
            context.startActivity(intent)
        }
    }

    private fun calculateItemWidth(): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        return (screenWidth / 2) - 30
    }
  private fun generateRandomColor(): Int {
    val r = Random.nextInt(256)
    val g = Random.nextInt(256)
    val b = Random.nextInt(256)
    return Color.rgb(r, g, b)
  }
}