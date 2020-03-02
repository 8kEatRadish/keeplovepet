package com.sniperking.keeplovepet

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sniperking.keeplovepet.utils.NavGraphBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        NavGraphBuilder.builder(navController)
        navView.setOnNavigationItemSelectedListener {
            navController.navigate(it.itemId)
            //true：选中；false：没有选中；
            !TextUtils.isEmpty(it.title)
        }
    }
}
