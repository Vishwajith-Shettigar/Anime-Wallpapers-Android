package lyka.anime.animewallpapers.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.annotation.MainThread
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import lyka.anime.animewallpapers.animeApplication
import lyka.anime.animewallpapers.Entity.FavouriteEntity
import lyka.anime.animewallpapers.R
import lyka.anime.animewallpapers.Repository.FavouriteRepository
import lyka.anime.animewallpapers.databinding.ActivityWallpaperBinding
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay


class WallpaperActivity : AppCompatActivity() {
  private lateinit var binding: ActivityWallpaperBinding

  @Inject
  lateinit var favouriteRepository: FavouriteRepository

  private var mInterstitialAd: InterstitialAd? = null
  private final val TAG = "WallpaperActivity"

  private var isSetWallpaper = false


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    requestWindowFeature(Window.FEATURE_NO_TITLE)
    window.setFlags(
      WindowManager.LayoutParams.FLAG_FULLSCREEN,
      WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
    (application as animeApplication).appComponent.injectWallpaperActivity(this)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_wallpaper)

    // Interstitial ad

    MobileAds.initialize(this)
    val adRequest = AdRequest.Builder().build()
    InterstitialAd.load(this,
      getString(R.string.wallpaper_screen_interstitial_ad_id),
      adRequest,
      object : InterstitialAdLoadCallback() {

        override fun onAdFailedToLoad(adError: LoadAdError) {
          adError.toString().let { Log.d(TAG, it) }
          mInterstitialAd = null
        }

        override fun onAdLoaded(interstitialAd: InterstitialAd) {
          Log.d(TAG, "Ad was loaded.")
          mInterstitialAd = interstitialAd

          mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
              // Ad dismissed callback
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
              // Ad failed to show callback
            }

            override fun onAdShowedFullScreenContent() {
              super.onAdShowedFullScreenContent()
              mInterstitialAd = null
            }
          }


        }

      }
    )

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
            (it as CircularProgressButton).startAnimation()
            CoroutineScope(Dispatchers.Main).launch {
              setWallpaperUsingIntent(resource)
            }
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
    isSetWallpaper = true
    // Get the content URI for the bitmap
    val contentUri = getImageUri(bitmap)
    val wallpaperIntent = Intent(Intent.ACTION_ATTACH_DATA)
    wallpaperIntent.addCategory(Intent.CATEGORY_DEFAULT)
    wallpaperIntent.setDataAndType(contentUri, "image/*")
    wallpaperIntent.putExtra("mimeType", "image/*")
    wallpaperIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
    wallpaperIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    startActivity(Intent.createChooser(wallpaperIntent, "Set As"))

    binding.setWallpaperbtn.apply {
      CoroutineScope(Dispatchers.Main).launch {
        delay(1000)
        revertAnimation()
        this@apply.setBackgroundResource(R.drawable.buttoncornerradius)
      }

    }

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
    if (isSetWallpaper)
      mInterstitialAd?.show(this)
    super.onBackPressed()

    val resultIntent = Intent()
    resultIntent.putExtra("modifiedData", "modifiedData")
    setResult(Activity.RESULT_OK, resultIntent)
    finish()
  }


}