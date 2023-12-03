package lyka.aot.aotwallpaper.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lyka.aot.aotwallpaper.Activities.CategoryActivity
import lyka.aot.aotwallpaper.Data.Category
import lyka.aot.aotwallpaper.R

class HomeCategoryAdapter(private val context: Context, private var data: MutableList<Category>) :
    RecyclerView.Adapter<HomeCategoryAdapter.Myviewholder>() {

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
            .into(holder.imageView)

        holder.imageView.setOnClickListener {
            var intent=Intent(context,CategoryActivity::class.java)
            intent.putExtra("cat_id",data[position].cat_id)
            intent.putExtra("cat_name",data[position].name)
            context.startActivity(intent)
        }
    }
}