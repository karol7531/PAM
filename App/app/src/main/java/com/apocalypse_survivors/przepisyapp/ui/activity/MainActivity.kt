package com.apocalypse_survivors.przepisyapp.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
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

//TODO:spinner selection on modify load,
// correct drawer selections,
// correct menu recipes observers
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


    //BUG: sometimes item does not light up
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        viewModel.categorySelected = findCategory(item.title.toString(), this)
        Log.i("MainActivity", "category selected: ${viewModel.categorySelected}")
        drawer.closeDrawers()
        callOnCategoryChanged()
        setToolbarTitle()
        return true
    }

    fun setToolbarTitle() {
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
        checkAllIfNothingSelected()

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
        hideKeyboard()
//        checkAllIfNothingSelected()
        return toRet
    }

    private fun checkAllIfNothingSelected() {
        val navView: NavigationView = findViewById(R.id.nav_view)
        //WARN: ALL item as 0 position
        if (viewModel.categorySelected == null){
            onNavigationItemSelected(navView.menu.getItem(0))
            navView.menu.getItem(0).isChecked = true
        }
//        if(getCheckedItem(navView) == -1){
//            onNavigationItemSelected(navView.menu.getItem(0))
//            navView.menu.getItem(0).isChecked = true
//        }
    }

    fun getCategorySelected() = viewModel.categorySelected

    private fun getCheckedItem(navigationView: NavigationView): Int {
        val menu = navigationView.menu
        for (i in 0 until menu.size()) {
            val item = menu[i]
            if (item.isChecked) {
                return i
            }
        }

        return -1
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setToolbarTitle()
        hideKeyboard()
    }

    fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?

        if (currentFocus != null) {
            inputManager!!.hideSoftInputFromWindow(
                currentFocus.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
            Log.d("MainActivity", "Hiding keyboard")
        }
    }
}
