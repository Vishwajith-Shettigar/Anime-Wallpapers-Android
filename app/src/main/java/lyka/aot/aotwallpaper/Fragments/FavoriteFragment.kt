package lyka.aot.aotwallpaper.Fragments

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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lyka.aot.aotwallpaper.Adapters.FavouriteAdapter
import lyka.aot.aotwallpaper.AotApplication
import lyka.aot.aotwallpaper.Entity.FavouriteEntity
import lyka.aot.aotwallpaper.R
import lyka.aot.aotwallpaper.Viewmodel.FavouriteViewmodel
import lyka.aot.aotwallpaper.Viewmodel.FavouriteViewmodelFactory
import lyka.aot.aotwallpaper.databinding.ActivityFavouriteBinding
import lyka.aot.aotwallpaper.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

  @Inject
  lateinit var favouriteViewmodelFactory: FavouriteViewmodelFactory
  private lateinit var binding: FragmentFavoriteBinding
  private lateinit var favouriteViewmodel: FavouriteViewmodel
  lateinit var favouriteAdapter: FavouriteAdapter
  private var datalist: MutableList<FavouriteEntity> = mutableListOf()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onResume() {
    super.onResume()
    lifecycleScope.launch {
      favouriteViewmodel.getAllFavourites()
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    // Inflate the layout for this fragment
    binding = FragmentFavoriteBinding.inflate(inflater, container, false)
    favouriteAdapter = FavouriteAdapter(datalist, activity as Context)

    (requireActivity().application as AotApplication).appComponent.injectFavoriteFragment(this)
    (requireActivity().application as AotApplication).appComponent.injectFavouriteAdapter(
      favouriteAdapter
    )
    lifecycleScope.launch {
      favouriteAdapter.notifyFavouriteChanges()
    }


    favouriteViewmodel = ViewModelProvider(
      this,
      favouriteViewmodelFactory

    ).get(FavouriteViewmodel::class.java)


    binding.favouritesRV.layoutManager = GridLayoutManager(activity as Context, 2)

    binding.favouritesRV.apply {
      adapter = favouriteAdapter
      isNestedScrollingEnabled = false
    }

    lifecycleScope.launch {
      favouriteViewmodel.favwallpapers.collectLatest {
        datalist = it
        if (datalist.size == 0) {
          binding.favouriteLabel.visibility = View.GONE
          binding.parentfavRv.visibility = View.GONE
          binding.nofav.visibility = View.VISIBLE
        } else {
          binding.favouriteLabel.visibility = View.VISIBLE
          binding.parentfavRv.visibility = View.VISIBLE
          binding.nofav.visibility = View.GONE
          favouriteAdapter.setdata(datalist)
        }

      }
    }
    return binding.root
  }
}