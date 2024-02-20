package lyka.aot.animewallpapers.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lyka.aot.animewallpapers.Adapters.CategoryAdapter
import lyka.aot.animewallpapers.AotApplication
import lyka.aot.animewallpapers.Entity.Wallpaper
import lyka.aot.animewallpapers.R
import lyka.aot.animewallpapers.Viewmodel.CategoryViewmodel
import lyka.aot.animewallpapers.Viewmodel.CategoryViewmodelFactory
import lyka.aot.animewallpapers.databinding.ActivityCategoryBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class WallpaperListActivity : AppCompatActivity() {

  private lateinit var binding: ActivityCategoryBinding
  private var datalist: MutableList<Wallpaper> = mutableListOf()
  private var cat_id: Int = 0
  private lateinit var wallpaperViewmodel: CategoryViewmodel

  @Inject
  lateinit var wallpaperViewmodelFactory: CategoryViewmodelFactory

  lateinit var categoryadapter: CategoryAdapter

  fun hideProgressbar() {
    binding.progressbar.visibility = View.GONE
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_category)
    categoryadapter = CategoryAdapter(datalist, this, binding.wallpaperRV, false, this)
    // injection
    (application as AotApplication).appComponent.injectCategoryActivity(this)
    (application as AotApplication).appComponent.injectCategoryAdapter(categoryadapter)

    //intent get extras
    val catname = intent.getStringExtra("cat_name")
    cat_id = intent.getIntExtra("cat_id", 0)


    val capitalizedText = catname?.substring(0, 1)?.uppercase() + catname?.substring(1)

    binding.categoryLabel.text = capitalizedText

    if (catname != null) {
      wallpaperViewmodelFactory.setCatName(catname, cat_id)
    }
    wallpaperViewmodel = ViewModelProvider(
      this,
      wallpaperViewmodelFactory
    ).get(CategoryViewmodel::class.java)

    binding.wallpaperRV.layoutManager = GridLayoutManager(this, 2)

    binding.wallpaperRV.apply {
      adapter = categoryadapter

    }


    binding.wallpaperRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

      }

      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as GridLayoutManager
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        val totalItemCount = layoutManager.itemCount

        if (lastVisibleItemPosition == totalItemCount - 1 && totalItemCount > 0 && !wallpaperViewmodel.noPages) {

          lifecycleScope.launch {
            wallpaperViewmodel.getMoreWallpapers()

          }
        }

      }


    })
    lifecycleScope.launchWhenStarted {

      wallpaperViewmodel.wallpapers.collect { dataList ->

        datalist = dataList
        categoryadapter.setdata(datalist)

      }
    }

  }

  @Deprecated("Deprecated in Java")
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    lifecycleScope.launch {
      categoryadapter.notifyFavouriteChanges()
    }
  }


}