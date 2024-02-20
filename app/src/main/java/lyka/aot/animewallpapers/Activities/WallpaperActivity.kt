package lyka.aot.animewallpapers.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import lyka.aot.animewallpapers.AotApplication
import lyka.aot.animewallpapers.Entity.FavouriteEntity
import lyka.aot.animewallpapers.R
import lyka.aot.animewallpapers.Repository.FavouriteRepository
import lyka.aot.animewallpapers.databinding.ActivityWallpaperBinding
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject


class WallpaperActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWallpaperBinding

    @Inject
    lateinit var favouriteRepository: FavouriteRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      requestWindowFeature(Window.FEATURE_NO_TITLE)
      window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
      )
      (application as AotApplication).appComponent.injectWallpaperActivity(this)
      binding = DataBindingUtil.setContentView(this, R.layout.activity_wallpaper)
        val intent = intent
        val imageUrl = intent.getStringExtra("imageUrl")
        var isFavourite = intent.getBooleanExtra("isFavourite", false)
        val id = intent.getStringExtra("id")
        if (isFavourite) {
            addFavourite()
        } else {
            removeFavourite()
        }

        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .placeholder(R.drawable.cornerradius)
            .error(R.drawable.cornerradius)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // Set the loaded bitmap as wallpaper when the button is clicked
                    binding.setWallpaperbtn.setOnClickListener {
                        setWallpaperUsingIntent(resource)
                    }

                    // Display the loaded bitmap in the ImageView
                    binding.image.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {

                }
            })

        binding.favbtn.setOnClickListener()
        {

            if (!isFavourite) {
                lifecycleScope.launch {
                    var favouriteEntity: FavouriteEntity? = null
                    if (id != null && imageUrl != null)
                        favouriteEntity = FavouriteEntity(id!!, imageUrl!!)

                    if (favouriteEntity != null) {

                        favouriteRepository.insert(favouriteEntity)
                    }

                    addFavourite()
                }
                isFavourite = true
            } else {
                lifecycleScope.launch {

                    if (id != null) {

                        favouriteRepository.deleteFavourite(id)
                    }
                    removeFavourite()
                }
                isFavourite = false
            }
        }
    }

    private fun setWallpaperUsingIntent(bitmap: Bitmap) {
        // Get the content URI for the bitmap
        val contentUri = getImageUri(bitmap)

        val wallpaperIntent = Intent(Intent.ACTION_ATTACH_DATA)
        wallpaperIntent.addCategory(Intent.CATEGORY_DEFAULT)
        wallpaperIntent.setDataAndType(contentUri, "image/png")
        wallpaperIntent.putExtra("mimeType", "image/png")
        wallpaperIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        wallpaperIntent.putExtra("finishActivityOnSaveCompleted", true)

        startActivity(Intent.createChooser(wallpaperIntent, "Set As"))
    }

    private fun getImageUri(bitmap: Bitmap): android.net.Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val displayName = "${System.currentTimeMillis()}.png" // Unique file name
        val path = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            displayName,
            null
        )
        return android.net.Uri.parse(path)
    }

    private fun addFavourite() {

        Glide.with(this)
            .load(R.drawable.baseline_favorite_24)
            .placeholder(R.drawable.baseline_favorite_24)
            .error(R.drawable.baseline_favorite_24)
            .into(binding.favbtn)
    }

    private fun removeFavourite() {
        Glide.with(this)
            .load(R.drawable.border_favourite)
            .placeholder(R.drawable.border_favourite)
            .error(R.drawable.border_favourite)
            .into(binding.favbtn)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val resultIntent = Intent()
        resultIntent.putExtra("modifiedData", "modifiedData")
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }


}