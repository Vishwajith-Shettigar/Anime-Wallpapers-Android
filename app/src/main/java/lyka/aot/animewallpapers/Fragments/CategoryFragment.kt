package lyka.aot.animewallpapers.Fragments

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
import lyka.aot.animewallpapers.Adapters.HomeCategoryAdapter
import lyka.aot.animewallpapers.AotApplication
import lyka.aot.animewallpapers.Data.Category
import lyka.aot.animewallpapers.R
import lyka.aot.animewallpapers.Viewmodel.HomeViewmodel
import lyka.aot.animewallpapers.Viewmodel.HomeViewmodelFactory
import lyka.aot.animewallpapers.databinding.ActivityHomeBinding
import lyka.aot.animewallpapers.databinding.FragmentCategoryBinding

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
    (requireActivity().application as AotApplication).appComponent.injectCategoryFragment(this)

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