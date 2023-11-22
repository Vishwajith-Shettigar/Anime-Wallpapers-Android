package com.example.aotwallpaper.Activities

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aotwallpaper.Adapters.HomeCategoryAdapter
import com.example.aotwallpaper.AotApplication
import com.example.aotwallpaper.Data.Category
import com.example.aotwallpaper.R
import com.example.aotwallpaper.Viewmodel.HomeViewmodel
import com.example.aotwallpaper.Viewmodel.HomeViewmodelFactory
import com.example.aotwallpaper.databinding.ActivityHomeBinding
import javax.inject.Inject


class HomeActivity : AppCompatActivity() {
    @Inject
    lateinit var categoryViewmodelFactory: HomeViewmodelFactory

    private lateinit var binding: ActivityHomeBinding

    private lateinit var categoryViewmodel: HomeViewmodel

    private var datalist:MutableList<Category> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        // injection
        (application as AotApplication).appComponent.injectHomeActivity(this)

        //viewmdoel
        categoryViewmodel = ViewModelProvider(
            this,
            categoryViewmodelFactory
        ).get(HomeViewmodel::class.java)


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
            Log.e("#", "facouite clicked")
        }


        binding.rateLayout.setOnClickListener {
            performHapticFeedback()
            repelAnimation(it)
            Log.e("#", "rate clicked")
        }


        binding.shareLayout.setOnClickListener {
            performHapticFeedback()
            repelAnimation(it)
            Log.e("#", "share clicked")
        }


        binding.aboutusLayout.setOnClickListener {
            performHapticFeedback()
            repelAnimation(it)
            Log.e("#", "about us clicked")
        }


        // set up category recuclrview


        datalist.add(
            Category(1,"friends",""),
        )
        datalist.add(
            Category(2,"romace",""),
        )
        datalist.add(
            Category(3,"chibi",""),
        )

        var catadapter = HomeCategoryAdapter(this, datalist)
        binding.categoryRV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.categoryRV.adapter = catadapter
        binding.categoryRV.isNestedScrollingEnabled = false


        lifecycleScope.launchWhenStarted {
            categoryViewmodel.categories.collect { dataList ->
                datalist=dataList
                catadapter.setdata(datalist)

            }
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
}