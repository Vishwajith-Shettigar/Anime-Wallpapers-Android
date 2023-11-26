package com.example.aotwallpaper.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aotwallpaper.Adapters.CategoryAdapter
import com.example.aotwallpaper.Adapters.FavouriteAdapter
import com.example.aotwallpaper.AotApplication
import com.example.aotwallpaper.Entity.FavouriteEntity
import com.example.aotwallpaper.Entity.Wallpaper
import com.example.aotwallpaper.R
import com.example.aotwallpaper.Viewmodel.CategoryViewmodel
import com.example.aotwallpaper.Viewmodel.FavouriteViewmodel
import com.example.aotwallpaper.Viewmodel.FavouriteViewmodelFactory
import com.example.aotwallpaper.Viewmodel.HomeViewmodelFactory
import com.example.aotwallpaper.databinding.ActivityFavouriteBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteActivity : AppCompatActivity() {
    @Inject
    lateinit var favouriteViewmodelFactory: FavouriteViewmodelFactory
    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var favouriteViewmodel: FavouriteViewmodel
    lateinit var favouriteAdapter: FavouriteAdapter
    private var datalist: MutableList<FavouriteEntity> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourite)
        favouriteAdapter = FavouriteAdapter(datalist, this)

        (application as AotApplication).appComponent.injectFavouriteActivity(this)
        (application as AotApplication).appComponent.injectFavouriteAdapter(favouriteAdapter)


        favouriteViewmodel = ViewModelProvider(
            this,
            favouriteViewmodelFactory

        ).get(FavouriteViewmodel::class.java)


        binding.favouritesRV.layoutManager = GridLayoutManager(this, 2)


        binding.favouritesRV.apply {
            adapter = favouriteAdapter
            isNestedScrollingEnabled = false
        }

        lifecycleScope.launch {
            favouriteViewmodel.favwallpapers.collectLatest {
                datalist=it
                favouriteAdapter.setdata(datalist)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        lifecycleScope.launch {
            favouriteAdapter.notifyFavouriteChanges()
        }
    }
}