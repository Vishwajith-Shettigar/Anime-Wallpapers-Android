package com.example.aotwallpaper.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.aotwallpaper.Adapters.CategoryAdapter
import com.example.aotwallpaper.AotApplication
import com.example.aotwallpaper.Entity.FavouriteEntity
import com.example.aotwallpaper.R
import com.example.aotwallpaper.Repository.FavouriteRepository
import com.example.aotwallpaper.databinding.ActivityWallpaperBinding
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject


class WallpaperActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWallpaperBinding

    @Inject
    lateinit var favouriteRepository: FavouriteRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            Log.e("#","fav")
            if (!isFavourite) {
                lifecycleScope.launch {
                    var favouriteEntity: FavouriteEntity? = null
                    if (id != null && imageUrl != null)
                        favouriteEntity = FavouriteEntity(id!!, imageUrl!!)

                    if (favouriteEntity != null) {
                        Log.e("#", "inserting")
                        favouriteRepository.insert(favouriteEntity)
                    }

                    addFavourite()
                }
                isFavourite = true
            } else {
                lifecycleScope.launch {

                    if (id != null) {
                        Log.e("#","deleet fav")
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
        wallpaperIntent.setDataAndType(contentUri, "image/jpeg")
        wallpaperIntent.putExtra("mimeType", "image/jpeg")
        wallpaperIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        wallpaperIntent.putExtra("finishActivityOnSaveCompleted", true)

        startActivity(Intent.createChooser(wallpaperIntent, "Set As"))
    }

    private fun getImageUri(bitmap: Bitmap): android.net.Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            "Choose one",
            null
        )
        return android.net.Uri.parse(path)
    }

    private fun addFavourite() {

        Glide.with(this)
            .load(R.drawable.baseline_favorite_24)
            .placeholder(R.drawable.cornerradius)
            .error(R.drawable.cornerradius)
            .into(binding.favbtn)
    }

    private fun removeFavourite() {
        Glide.with(this)
            .load(R.drawable.baseline_favorite_border_24)
            .placeholder(R.drawable.cornerradius)
            .error(R.drawable.cornerradius)
            .into(binding.favbtn)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.e("#","on back pressed")
        val resultIntent = Intent()
        resultIntent.putExtra("modifiedData", "modifiedData")
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }


}