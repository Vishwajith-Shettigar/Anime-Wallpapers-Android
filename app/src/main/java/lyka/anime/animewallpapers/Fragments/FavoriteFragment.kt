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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lyka.anime.animewallpapers.Adapters.FavouriteAdapter
import lyka.anime.animewallpapers.animeApplication
import lyka.anime.animewallpapers.Entity.FavouriteEntity
import lyka.anime.animewallpapers.R
import lyka.anime.animewallpapers.Viewmodel.FavouriteViewmodel
import lyka.anime.animewallpapers.Viewmodel.FavouriteViewmodelFactory
import lyka.anime.animewallpapers.databinding.ActivityFavouriteBinding
import lyka.anime.animewallpapers.databinding.FragmentFavoriteBinding

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

    (requireActivity().application as animeApplication).appComponent.injectFavoriteFragment(this)
    (requireActivity().application as animeApplication).appComponent.injectFavouriteAdapter(
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