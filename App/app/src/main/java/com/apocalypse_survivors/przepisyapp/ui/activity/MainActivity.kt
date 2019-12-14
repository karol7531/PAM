package com.apocalypse_survivors.przepisyapp.ui.activity

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
import com.apocalypse_survivors.przepisyapp.OnCategoryChangedListener
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.findCategory
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var drawer: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        //toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //drawer
        drawer = findViewById(R.id.drawer_layout)

        //navigation
        setupNavigation()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        viewModel.categorySelected = findCategory(item.title.toString(), this)
        Log.i("MainActivity", "category selected: ${viewModel.categorySelected}")
        drawer.closeDrawers()
        callOnCategoryChanged()
        setToolbarTitle()
        return true
    }

    private fun setToolbarTitle() {
        if (viewModel.categorySelected != null) {
            setToolbarTitle(viewModel.categorySelected?.getLabel(this))
        }
    }

    fun setToolbarTitle(title: String?){
        supportActionBar?.title = title
        Log.d("MainActivity", "toolbar title setted")
    }

    // calls OnCategoryChanged() on every fragment which is OnCategoryChangedListener
    private fun callOnCategoryChanged() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val fragments = navHostFragment?.childFragmentManager?.fragments
        fragments?.forEach {
            if (it != null && it is OnCategoryChangedListener){
                it.onCategoryChanged(viewModel.categorySelected!!)
            }
        }
    }

    private fun setupNavigation(){
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, drawer)
        navView.setNavigationItemSelectedListener(this)

        //sets the ALL item to be checked on startup
        onNavigationItemSelected(navView.menu.getItem(0))
        navView.menu.getItem(0).isChecked = true

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
        val toRet = navController.navigateUp(drawerLayout = findViewById(R.id.drawer_layout)) || super.onSupportNavigateUp()
        setToolbarTitle()
        return toRet
    }

    fun getCategorySelected() = viewModel.categorySelected
}
