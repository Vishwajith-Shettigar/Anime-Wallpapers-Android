package lyka.anime.animewallpapers.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lyka.anime.animewallpapers.Adapters.HomeCategoryAdapter
import lyka.anime.animewallpapers.animeApplication
import lyka.anime.animewallpapers.Data.Category
import lyka.anime.animewallpapers.R
import lyka.anime.animewallpapers.Viewmodel.HomeViewmodel
import lyka.anime.animewallpapers.Viewmodel.HomeViewmodelFactory
import lyka.anime.animewallpapers.databinding.ActivityHomeBinding
import lyka.anime.animewallpapers.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

  @Inject
  lateinit var categoryViewmodelFactory: HomeViewmodelFactory

  private lateinit var binding: FragmentCategoryBinding
  private var datalist: MutableList<Category> = mutableListOf()
  private lateinit var categoryViewmodel: HomeViewmodel
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    binding = FragmentCategoryBinding.inflate(inflater, container, false)
    (requireActivity().application as animeApplication).appComponent.injectCategoryFragment(this)

    binding.categoryRV.visibility = View.GONE
    binding.skeletonparent.visibility = View.VISIBLE

  categoryViewmodel = ViewModelProvider(
      this,
      categoryViewmodelFactory
    ).get(HomeViewmodel::class.java)

    // set up category recuclrview

    var catadapter = HomeCategoryAdapter(activity as Context, datalist)
    binding.categoryRV.layoutManager =
      GridLayoutManager(activity as Context, 2)
    binding.categoryRV.adapter = catadapter
    binding.categoryRV.isNestedScrollingEnabled = false

    binding.swipeRefreshLayout.setOnRefreshListener {
      categoryViewmodel.getCategories()
      lifecycleScope.launch {
        delay(2000)
        binding.swipeRefreshLayout.isRefreshing = false

      }
    }
    lifecycleScope.launchWhenStarted {
      categoryViewmodel.categories.collect { dataList ->
        datalist = dataList
        catadapter.setdata(datalist)
        if (dataList.size != 0) {
          binding.categoryRV.visibility = View.VISIBLE
          binding.skeletonparent.visibility = View.GONE
        }
      }
    }

      return binding.root
  }

}