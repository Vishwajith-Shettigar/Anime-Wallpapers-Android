package lyka.aot.animewallpapers.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import lyka.aot.animewallpapers.Adapters.FavouriteAdapter
import lyka.aot.animewallpapers.AotApplication
import lyka.aot.animewallpapers.Entity.FavouriteEntity
import lyka.aot.animewallpapers.R
import lyka.aot.animewallpapers.Viewmodel.FavouriteViewmodel
import lyka.aot.animewallpapers.Viewmodel.FavouriteViewmodelFactory
import lyka.aot.animewallpapers.databinding.ActivityFavouriteBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import lyka.aot.animewallpapers.Fragments.FavoriteFragment

class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourite)

      val fragmentManager = supportFragmentManager
      val fragmentTransaction = fragmentManager.beginTransaction()
      val fragment = FavoriteFragment()
      fragmentTransaction.replace(R.id.fragmentcontainer, fragment)
      fragmentTransaction.commit()
    }

}