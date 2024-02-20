package lyka.anime.animewallpapers.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import lyka.anime.animewallpapers.Adapters.SplashSliderAdapter
import lyka.anime.animewallpapers.animeApplication
import lyka.anime.animewallpapers.Fragments.Splash1Fragment
import lyka.anime.animewallpapers.Fragments.Splash2Fragment
import lyka.anime.animewallpapers.Fragments.Splash3Fragment
import lyka.anime.animewallpapers.R
import lyka.anime.animewallpapers.Util.SharedPreferencesManager
import lyka.anime.animewallpapers.databinding.SplashScreenActivityBinding
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashscreenActivity : AppCompatActivity() {
  private lateinit var binding: SplashScreenActivityBinding

  @Inject
  lateinit var sharedPreferencesManager: SharedPreferencesManager

  @Inject
  lateinit var firestore: FirebaseFirestore
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    (application as animeApplication).appComponent.injectSplashScreenActivity(this)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    window.setFlags(
      WindowManager.LayoutParams.FLAG_FULLSCREEN,
      WindowManager.LayoutParams.FLAG_FULLSCREEN
    )

    binding = DataBindingUtil.setContentView(this, R.layout.splash_screen_activity)

    GlobalScope.launch {
      try {
        val today = Date()
        // Format the date to a string in the format "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateString = dateFormat.format(today)

        val collectionRef = firestore.collection("anime").document("traffic").collection("views")

        collectionRef.whereEqualTo("date", dateString).get()
          .addOnSuccessListener { documents ->

            if (documents.isEmpty) {
              val newDocument = hashMapOf(
                "date" to dateString,
                "views" to 1
              )
              collectionRef
                .add(newDocument)
                .addOnSuccessListener {
                }
                .addOnFailureListener {
                }
            } else {
              val document = documents.documents.get(0)
              val docref = collectionRef
                .document(document.id)
              val curviews = document.getLong("views")

              docref.update("views", curviews!! + 1)
                .addOnSuccessListener {
                }
                .addOnFailureListener {

                }
            }
          }
      } catch (e: Exception) {

      }
    }

    if (!sharedPreferencesManager.getIsSliderOff()) {
      binding.singlescreenParent.visibility = View.GONE
      binding.sliderParent.visibility = View.VISIBLE
      sharedPreferencesManager.offSSlider()
    } else {
      binding.singlescreenParent.visibility = View.VISIBLE
      binding.sliderParent.visibility = View.GONE
      Handler().postDelayed({

        val intent = Intent(this, lyka.anime.animewallpapers.Activities.HomeActivity::class.java)
        startActivity(intent)
        finish()
      }, 2000)
    }
    val fragmentList: MutableList<Fragment> = ArrayList()
    fragmentList.add(Splash1Fragment())
    fragmentList.add(Splash2Fragment())
    fragmentList.add(Splash3Fragment())

    val sliderAdapter = SplashSliderAdapter(supportFragmentManager, lifecycle, fragmentList)
    binding.splashscreenviewpager.adapter = sliderAdapter

    binding.splashscreenviewpager.registerOnPageChangeCallback(object :
      ViewPager2.OnPageChangeCallback() {

      override fun onPageScrolled(
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int,
      ) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)

        binding.dotone.setBackgroundResource(R.drawable.dotsfragback)
        binding.dottwo.setBackgroundResource(R.drawable.dotsfragback)
        binding.dotthree.setBackgroundResource(R.drawable.dotsfragback)


        if (position == 0)
          binding.dotone.setBackgroundResource(R.drawable.dotsfragactive)
        else if (position == 1)
          binding.dottwo.setBackgroundResource(
            R.drawable.dotsfragactive
          )
        else if (position == 2)
          binding.dotthree.setBackgroundResource(R.drawable.dotsfragactive)

      }
    })


  }
}