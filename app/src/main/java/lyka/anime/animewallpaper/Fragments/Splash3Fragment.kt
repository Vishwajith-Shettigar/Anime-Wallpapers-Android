package lyka.anime.animewallpaper.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import lyka.anime.animewallpaper.Activities.HomeActivity
import lyka.anime.animewallpaper.R
import lyka.anime.animewallpaper.databinding.FragmentSplash3Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Splash3Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Splash3Fragment : Fragment() {

    private lateinit var binding:FragmentSplash3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash3, container, false)

        binding.startbtn.setOnClickListener {
            startActivity(Intent(activity,HomeActivity::class.java))
            activity?.finish()
        }
        return binding.root
    }


}