package lyka.anime.animewallpapers.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class SplashSliderAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val fragmentlist: List<Fragment>? = null
) : FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun createFragment(position: Int): Fragment {
        return fragmentlist!![position]
    }


    override fun getItemCount(): Int {
        return fragmentlist!!.size
    }
}