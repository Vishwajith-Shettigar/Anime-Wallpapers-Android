package lyka.anime.animewallpapers.Activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import lyka.anime.animewallpapers.Adapters.HomeCategoryAdapter
import lyka.anime.animewallpapers.animeApplication
import lyka.anime.animewallpapers.Data.Category
import lyka.anime.animewallpapers.R
import lyka.anime.animewallpapers.Viewmodel.HomeViewmodel
import lyka.anime.animewallpapers.Viewmodel.HomeViewmodelFactory
import lyka.anime.animewallpapers.databinding.ActivityHomeBinding
import javax.inject.Inject
import lyka.anime.animewallpapers.Adapters.ViewPagerAdapter
import lyka.anime.animewallpapers.Fragments.HomeFragment


class HomeActivity : AppCompatActivity() {
  @Inject
  lateinit var categoryViewmodelFactory: HomeViewmodelFactory

  private lateinit var binding: ActivityHomeBinding

  private lateinit var categoryViewmodel: HomeViewmodel

  private var datalist: MutableList<Category> = mutableListOf()
  val viewPagerAdapter = ViewPagerAdapter(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    checkInternetConnectivity()
    binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

    // injection
    (application as animeApplication).appComponent.injectHomeActivity(this)



    binding.homescreenVp.adapter = viewPagerAdapter

    binding.bottomnavigationview.selectedItemId = R.id.home
    binding.bottomnavigationview.setOnItemSelectedListener { item ->
      when (item.itemId) {
        R.id.home -> {
          binding.homescreenVp.setCurrentItem(0, true)
        }
        R.id.category -> binding.homescreenVp.setCurrentItem(1, true)
        R.id.favorite -> binding.homescreenVp.setCurrentItem(2, true)
      }
      true

    }
    binding.homescreenVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
      override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        // Update the selected item in BottomNavigationView when ViewPager2's current item changes
        binding.bottomnavigationview.selectedItemId = when (position) {
          0 -> {
            R.id.home
          }
          1 -> R.id.category
          2 -> R.id.favorite
          else -> -1 // Handle other cases as needed
        }
      }
    })

    // drawer
    val toggle = ActionBarDrawerToggle(
      this, binding.drawableLayout,
      R.string.nav_open, R.string.nav_close
    )
    binding.drawableLayout.addDrawerListener(toggle)
    toggle.syncState()

    binding.opendrawer.setOnClickListener {
      binding.drawableLayout.openDrawer(GravityCompat.START)
    }

    binding.favouriteLayout.setOnClickListener {
      performHapticFeedback()
      repelAnimation(it)
      startActivity(Intent(this, lyka.anime.animewallpapers.Activities.FavouriteActivity::class.java))
    }


    binding.rateLayout.setOnClickListener {
      performHapticFeedback()
      repelAnimation(it)
      rateApp()

    }


    binding.shareLayout.setOnClickListener {
      performHapticFeedback()
      repelAnimation(it)

      shareApp()
    }


    binding.aboutusLayout.setOnClickListener {
      performHapticFeedback()
      repelAnimation(it)
      startActivity(Intent(this, lyka.anime.animewallpapers.Activities.AboutUsActivity::class.java))

    }

  }

  private fun performHapticFeedback() {
    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    // Check if the device has a vibrator
    if (vibrator.hasVibrator()) {
      // If the device is running on Android 26 (Build.VERSION_CODES.O) or higher,
      // use the VibrationEffect for more control
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val vibrationEffect =
          VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(vibrationEffect)
      } else {
        // For devices below Android 26, use the deprecated method
        vibrator.vibrate(50)
      }
    }
  }

  private fun repelAnimation(view: View) {
    val animation = TranslateAnimation(0f, 0f, 0f, -50f)
    animation.duration = 200
    animation.interpolator = android.view.animation.AccelerateInterpolator()

    view.startAnimation(animation)
  }

  private fun shareApp() {
    val appPackageName = packageName
    val appName = getString(R.string.app_name)
    val playStoreLink = "https://play.google.com/store/apps/details?id=$appPackageName"

    val shareMessage = "Check out Anime wallpapers App on the Play Store:\n$playStoreLink"

    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, shareMessage)

    // Verify that the intent will resolve to an activity
    if (intent.resolveActivity(packageManager) != null) {
      startActivity(Intent.createChooser(intent, "Share $appName using"))
    }
  }

  private fun rateApp() {
    val appPackageName = packageName

    try {
      startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
    } catch (e: android.content.ActivityNotFoundException) {
      // If Google Play Store is not installed on the device, open the browser
      startActivity(
        Intent(
          Intent.ACTION_VIEW,
          Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
        )
      )
    }
  }

  private fun checkInternetConnectivity() {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo

    if (networkInfo == null || !networkInfo.isConnected) {
      // No internet connection, show warning
      showNoInternetWarning()
    }
  }

  private fun showNoInternetWarning() {
    // Show a dialog or toast message to inform the user about the lack of internet connectivity
    AlertDialog.Builder(this)
      .setTitle("No Internet Connection")
      .setMessage("Please check your internet connection and try again.")
      .setPositiveButton("OK") { dialog, _ ->
        dialog.dismiss()
      }
      .show()
  }
}