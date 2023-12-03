package lyka.aot.aotwallpaper.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import lyka.aot.aotwallpaper.Adapters.SplashSliderAdapter
import lyka.aot.aotwallpaper.AotApplication
import lyka.aot.aotwallpaper.Fragments.Splash1Fragment
import lyka.aot.aotwallpaper.Fragments.Splash2Fragment
import lyka.aot.aotwallpaper.Fragments.Splash3Fragment
import lyka.aot.aotwallpaper.R
import lyka.aot.aotwallpaper.Util.SharedPreferencesManager
import lyka.aot.aotwallpaper.databinding.SplashScreenActivityBinding
import javax.inject.Inject

class SplashscreenActivity : AppCompatActivity() {
    private lateinit var binding: SplashScreenActivityBinding

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as AotApplication).appComponent.injectSplashScreenActivity(this)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding = DataBindingUtil.setContentView(this, R.layout.splash_screen_activity)

        if (!sharedPreferencesManager.getIsSliderOff()) {
            binding.singlescreenParent.visibility = View.GONE
            binding.sliderParent.visibility = View.VISIBLE
            sharedPreferencesManager.offSSlider()
        } else {
            binding.singlescreenParent.visibility = View.VISIBLE
            binding.sliderParent.visibility = View.GONE
            Handler().postDelayed({

                val intent = Intent(this, HomeActivity::class.java)
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
                positionOffsetPixels: Int
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