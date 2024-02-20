package lyka.aot.aotwallpaper.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import lyka.aot.aotwallpaper.Adapters.FavouriteAdapter
import lyka.aot.aotwallpaper.AotApplication
import lyka.aot.aotwallpaper.Entity.FavouriteEntity
import lyka.aot.aotwallpaper.R
import lyka.aot.aotwallpaper.Viewmodel.FavouriteViewmodel
import lyka.aot.aotwallpaper.Viewmodel.FavouriteViewmodelFactory
import lyka.aot.aotwallpaper.databinding.ActivityFavouriteBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import lyka.aot.aotwallpaper.Fragments.FavoriteFragment

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