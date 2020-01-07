package com.example.managertransactio.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import com.example.managertransactio.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {



    val  TAG = "content_fragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
//        bottom_nav.setOnNavigationItemSelectedListener(navListener)
//        bottom_nav.setSelectedItemId(R.id.ic_nav_add)


        return view
    }

//    private val navListener =
//        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.ic_nav_home -> {
//                    val fragment = TotalFragment()
//                    val tag = TotalFragment().TAG
//                    commitFragmentAddBackStack(fragment, tag)
//                    return@OnNavigationItemSelectedListener true
//                }
//                R.id.ic_nav_add -> {
//                    val fragment = AddFragment()
//                    val tag = AddFragment().TAG
//                    commitFragmentAddBackStack(fragment, tag)
//                    return@OnNavigationItemSelectedListener true
//                }
//                R.id.ic_nav_his -> {
//                    val fragment = HistoryFragment()
//                    val tag = HistoryFragment().TAG
//                    commitFragmentAddBackStack(fragment, tag)
//                    return@OnNavigationItemSelectedListener true
//                }
//            }
//
//            false
//        }

    fun commitFragmentAddBackStack(fragment: Fragment, tagName: String) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fm_content_container, fragment, tagName)
            .addToBackStack(tagName)
            .commit()
    }



}
