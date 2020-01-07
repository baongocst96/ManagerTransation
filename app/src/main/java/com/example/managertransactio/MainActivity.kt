package com.example.managertransactio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.managertransactio.Fragments.AddFragment
import com.example.managertransactio.Fragments.HistoryFragment
import com.example.managertransactio.Fragments.TotalFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*





class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        commitFragment(MainFragment(), MainFragment().TAG)


        bottom_nav_main.setOnNavigationItemSelectedListener(navListener)
        bottom_nav_main.setSelectedItemId(R.id.ic_nav_home)


    }

    private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.ic_nav_home -> {
                    val fragment = TotalFragment()
                    val tag = TotalFragment().TAG
                    commitFragment(fragment, tag)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.ic_nav_add -> {
                    val fragment = AddFragment()
                    val tag = AddFragment().TAG
                    commitFragment(fragment, tag)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.ic_nav_his -> {
                    val fragment = HistoryFragment()
                    val tag = HistoryFragment().TAG
                    commitFragment(fragment, tag)
                    return@OnNavigationItemSelectedListener true
                }
            }

            false
        }
    fun commitFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_main, fragment)
            .addToBackStack(tag)
            .commit()
    }
}
