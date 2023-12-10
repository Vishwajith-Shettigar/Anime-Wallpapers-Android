package lyka.anime.animewallpaper.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import lyka.anime.animewallpaper.Adapters.FavouriteAdapter
import lyka.anime.animewallpaper.AotApplication
import lyka.anime.animewallpaper.Entity.FavouriteEntity
import lyka.anime.animewallpaper.R
import lyka.anime.animewallpaper.Viewmodel.FavouriteViewmodel
import lyka.anime.animewallpaper.Viewmodel.FavouriteViewmodelFactory
import lyka.anime.animewallpaper.databinding.ActivityFavouriteBinding
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
                if(datalist.size==0){
                    binding.favouriteLabel.visibility=View.GONE
                    binding.parentfavRv.visibility=View.GONE
                    binding.nofav.visibility=View.VISIBLE
                }else {
                    binding.favouriteLabel.visibility = View.VISIBLE
                    binding.parentfavRv.visibility = View.VISIBLE
                    binding.nofav.visibility = View.GONE
                    favouriteAdapter.setdata(datalist)
                }

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