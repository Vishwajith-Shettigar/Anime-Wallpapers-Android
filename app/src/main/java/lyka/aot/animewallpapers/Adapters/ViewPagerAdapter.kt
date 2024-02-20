package lyka.aot.animewallpapers.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import lyka.aot.animewallpapers.Fragments.CategoryFragment
import lyka.aot.animewallpapers.Fragments.FavoriteFragment
import lyka.aot.animewallpapers.Fragments.HomeFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

  override fun getItemCount(): Int = 3 // Number of fragments

  override fun createFragment(position: Int): Fragment {
    return when (position) {
      0 -> HomeFragment()
      1 -> CategoryFragment()
      2-> FavoriteFragment()
      // Add more fragments if needed
      else -> throw IllegalArgumentException("Invalid position")
    }
  }
}
