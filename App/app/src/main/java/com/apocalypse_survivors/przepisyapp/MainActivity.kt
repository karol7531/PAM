package com.apocalypse_survivors.przepisyapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var categorySelected :String? = null
        private set

    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //drawer
        drawer = findViewById(R.id.drawer_layout)

        //navigation
        setupNavigation()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        categorySelected = item.title.toString()
        Log.i("MainActivity", "category selected: $categorySelected")
        drawer.closeDrawers()
        //IDEA: change toolbar string
        return true
    }

    private fun setupNavigation(){
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, drawer)
        navView.setNavigationItemSelectedListener(this)
        Log.d("MainActivity", "Navigation setted up")
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        Log.i("MainActivity", "Navigated up")
        return navController.navigateUp(drawerLayout = findViewById(R.id.drawer_layout)) || super.onSupportNavigateUp()
    }
}
