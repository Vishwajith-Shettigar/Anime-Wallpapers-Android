package com.example.aotwallpaper.Activities

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.aotwallpaper.R
import com.example.aotwallpaper.databinding.ActivityWallpaperBinding
import java.io.ByteArrayOutputStream


class WallpaperActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWallpaperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallpaper)

        val intent = intent
        val imageUrl = intent.getStringExtra("imageUrl")
        val isFavourite = intent.getBooleanExtra("isFavourite", false)


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


}