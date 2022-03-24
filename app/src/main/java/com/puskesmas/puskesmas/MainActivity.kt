package com.puskesmas.puskesmas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.puskesmas.puskesmas.ui.BerandaFragment
import com.puskesmas.puskesmas.ui.LIhatGrafikFragment
import com.puskesmas.puskesmas.ui.LihatDataFragment

class MainActivity : AppCompatActivity() {
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehome,
                        BerandaFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_report -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehome,
                        LihatDataFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true

                }
                R.id.navigation_produk -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehome,
                        LIhatGrafikFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true

                }

            }

            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_viewhome)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(BerandaFragment())

    }

    private fun moveToFragment(fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.framehome, fragment)
        fragmentTrans.commit()
    }

}