package lyka.anime.animewallpapers.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lyka.anime.animewallpapers.Activities.WallpaperListActivity
import lyka.anime.animewallpapers.Activities.WallpaperActivity
import lyka.anime.animewallpapers.Adapters.CategoryAdapter
import lyka.anime.animewallpapers.animeApplication
import lyka.anime.animewallpapers.Data.Category
import lyka.anime.animewallpapers.Entity.Wallpaper
import lyka.anime.animewallpapers.Viewmodel.FeatureAndTrendingViewmodel
import lyka.anime.animewallpapers.Viewmodel.FeatureAndTrendingViewmodelFactory
import lyka.anime.animewallpapers.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
  @Inject
  lateinit var featureAndTrendingViewmodelFactory: FeatureAndTrendingViewmodelFactory

  private lateinit var binding: FragmentHomeBinding
  private lateinit var featureAndTrendingViewmodel: FeatureAndTrendingViewmodel

  private lateinit var threeImages: MutableList<Wallpaper>
  private lateinit var category: Category

  private var fromWallpaperActivity = false

  lateinit var categoryadapter: CategoryAdapter
  private var datalist: MutableList<Wallpaper> = mutableListOf()

  override fun onResume() {
    super.onResume()


    lifecycleScope.launch {
      if (::categoryadapter.isInitialized) {
        categoryadapter.notifyFavouriteChanges()
      } else {
        // Handle initialization if needed
      }
    }
    fromWallpaperActivity = false


  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {

    binding = FragmentHomeBinding.inflate(inflater, container, false)

    // banner add
    MobileAds.initialize(requireContext())
    val adRequest = AdRequest.Builder().build()
    binding.homescreenAdview.loadAd(adRequest)


    categoryadapter = CategoryAdapter(datalist, activity as Context, binding.trendingRv, true)

    // injection
    (requireActivity().application as animeApplication).appComponent.injectHomeFragment(this)
    (requireActivity().application as animeApplication).appComponent.injectCategoryAdapter(
      categoryadapter
    )

    featureAndTrendingViewmodel = ViewModelProvider(
      this,
      featureAndTrendingViewmodelFactory
    ).get(FeatureAndTrendingViewmodel::class.java)


    binding.trendingRv.layoutManager = GridLayoutManager(activity as Context, 2)

    binding.trendingRv.apply {
      adapter = categoryadapter
      isNestedScrollingEnabled = false
    }

    binding.swipeRefreshLayout.setOnRefreshListener {
      featureAndTrendingViewmodel.getCategory()
      featureAndTrendingViewmodel.getThreeWallpapers()
      featureAndTrendingViewmodel.getTrendingWallpapers()

      lifecycleScope.launch {
        delay(2000)
        binding.swipeRefreshLayout.isRefreshing = false

      }
    }


    lifecycleScope.launchWhenStarted {

      featureAndTrendingViewmodel.trendingWallpapers.collect { dataList ->

        datalist = dataList
        categoryadapter.setdata(datalist,true)


      }
    }
    lifecycleScope.launch {
      featureAndTrendingViewmodel.threeWallpapers.collectLatest {
        if (it.size != 0) {
          threeImages = it

          Glide.with(this@HomeFragment).load(it[0].imageUrl).into(binding.homescreenicon1)
          Glide.with(this@HomeFragment).load(it[1].imageUrl).into(binding.homescreenicon2)
          Glide.with(this@HomeFragment).load(it[2].imageUrl).into(binding.homescreenicon3)

        } else {

          var colorDrawable = ColorDrawable(generateRandomColor())
          binding.homescreenicon1.background = colorDrawable

          colorDrawable = ColorDrawable(generateRandomColor())
          binding.homescreenicon2.background = colorDrawable

          colorDrawable = ColorDrawable(generateRandomColor())
          binding.homescreenicon3.background = colorDrawable

        }
      }
    }
    binding.homescreenicon1.setOnClickListener {
      lifecycleScope.launch {
        try {

          val isFavourite = featureAndTrendingViewmodel.isFavorite(threeImages[0].id)
          val intent = Intent(context, WallpaperActivity::class.java)
          intent.putExtra("imageUrl", threeImages[0].imageUrl)
          intent.putExtra("isFavourite", isFavourite)
          intent.putExtra("id", threeImages[0].id)
          (context as Activity).startActivityForResult(intent, 1001)
        }
        catch (_:Exception){

        }
      }
    }

    binding.homescreenicon2.setOnClickListener {
      lifecycleScope.launch {
        try {
          val isFavourite = featureAndTrendingViewmodel.isFavorite(threeImages[1].id)
          val intent = Intent(context, WallpaperActivity::class.java)
          intent.putExtra("imageUrl", threeImages[1].imageUrl)
          intent.putExtra("isFavourite", isFavourite)
          intent.putExtra("id", threeImages[1].id)
          (context as Activity).startActivityForResult(intent, 1001)
        }catch (_:Exception){}
      }
    }
    binding.homescreenicon3.setOnClickListener {
      lifecycleScope.launch {
        try {
        val isFavourite = featureAndTrendingViewmodel.isFavorite(threeImages[2].id)
        val intent = Intent(context, WallpaperActivity::class.java)
        intent.putExtra("imageUrl", threeImages[2].imageUrl)
        intent.putExtra("isFavourite", isFavourite)
        intent.putExtra("id", threeImages[2].id)
        (context as Activity).startActivityForResult(intent, 1001)
        }catch (_:Exception){}
      }




    }


    lifecycleScope.launch {
      featureAndTrendingViewmodel.category.collectLatest {
        if (it.cat_id == -1) {
          val color = generateRandomColor()
          val colorDrawable = ColorDrawable(color)
          binding.featuredImage.background = colorDrawable
        } else {
          category = it
          Glide.with(this@HomeFragment).load(category.imageUrl).into(binding.featuredImage)
        }
      }

    }



    binding.featuredImage.setOnClickListener {
      try{
      var intent = Intent(context, WallpaperListActivity::class.java)
      intent.putExtra("cat_id", category.cat_id)
      intent.putExtra("cat_name", category.name)
      startActivity(intent)
    }catch (_:Exception){}
    }



    return binding.root

  }

  private fun generateRandomColor(): Int {
    val r = Random.nextInt(256)
    val g = Random.nextInt(256)
    val b = Random.nextInt(256)
    return Color.rgb(r, g, b)
  }

}


