package com.example.aotwallpaper.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aotwallpaper.Adapters.CategoryAdapter
import com.example.aotwallpaper.AotApplication
import com.example.aotwallpaper.Data.Wallpaper
import com.example.aotwallpaper.R
import com.example.aotwallpaper.Viewmodel.CategoryViewmodel
import com.example.aotwallpaper.Viewmodel.CategoryViewmodelFactory
import com.example.aotwallpaper.databinding.ActivityCategoryBinding
import javax.inject.Inject

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private var datalist: MutableList<Wallpaper> = mutableListOf()
    private var cat_id: Int = 0
    private lateinit var wallpaperViewmodel: CategoryViewmodel
    @Inject
    lateinit var wallpaperViewmodelFactory: CategoryViewmodelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categoryadapter = CategoryAdapter(datalist, this)
        // injection
        (application as AotApplication).appComponent.injectCategoryActivity(this)
        (application as AotApplication).appComponent.injectCategoryAdapter(categoryadapter)

        //intent get extras
        val catname = intent.getStringExtra("cat_name")
        cat_id = intent.getIntExtra("cat_id", 0)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_category)

        val capitalizedText = catname?.substring(0, 1)?.uppercase() + catname?.substring(1)

        binding.categoryLabel.text = capitalizedText

        if (catname != null) {
            wallpaperViewmodelFactory.setCatName(catname)
        }
        wallpaperViewmodel = ViewModelProvider(
            this,
            wallpaperViewmodelFactory
        ).get(CategoryViewmodel::class.java)




        binding.wallpaperRV.layoutManager = GridLayoutManager(this, 2)



        binding.wallpaperRV.apply {
            adapter = categoryadapter
            isNestedScrollingEnabled = false
        }

        lifecycleScope.launchWhenStarted {
            wallpaperViewmodel.wallpapers.collect { dataList ->
                datalist=dataList
                categoryadapter.setdata(datalist)

            }
        }

    }
}