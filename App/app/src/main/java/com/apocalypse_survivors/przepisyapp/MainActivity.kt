package com.apocalypse_survivors.przepisyapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryType
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var categorySelected :CategoryType? = null
        private set

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        //toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //drawer
        drawer = findViewById(R.id.drawer_layout)

        //navigation
        setupNavigation()

//        viewModel.populateDB()

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        categorySelected = findCategory(item.title.toString(), this)
        Log.i("MainActivity", "category selected: $categorySelected")
        drawer.closeDrawers()
        callOnCategoryChanged()
        //IDEA: change toolbar string
        return true
    }

    private fun callOnCategoryChanged() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val fragments = navHostFragment?.childFragmentManager?.fragments
        fragments?.forEach {
            if (it != null && it is OnCategoryChangedListener){
                it.onCategoryChanged(categorySelected!!)
            }
        }
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
