package com.apocalypse_survivors.przepisyapp.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apocalypse_survivors.przepisyapp.MainActivity
import com.apocalypse_survivors.przepisyapp.OnCategoryChangedListener
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryType
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MenuFragment : Fragment(), OnCategoryChangedListener {

    private lateinit var viewModel: MenuViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeAdapter : RecipeAdapter
    private lateinit var fab: FloatingActionButton
    private var categoryType: CategoryType? = null

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_menu, container, false)

        recipeAdapter = RecipeAdapter(activity!!)
        fab = root.findViewById(R.id.menu_fab_modify)
        recyclerView  = root.findViewById(R.id.menu_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = recipeAdapter

        recipeAdapter.setOnItemCLickListener(object : RecipeAdapter.OnItemClickListener{
            override fun onItemClick(recipe: RecipeEntity) {
                //TODO: pass arguments
                Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(R.id.action_nav_menu_to_nav_recipe)
            }

        })

        setupData()

        //fab
        fab.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(R.id.action_nav_menu_to_nav_modify)
        }

        return root
    }

    private fun setupData() {
        //set items to recycler and observe live data
        categoryType = (activity as MainActivity).categorySelected
        if (categoryType == null) {
            viewModel.getAll().observe(this,
                Observer<List<RecipeEntity>> { recipes -> recipeAdapter.setRecipes(recipes!!) })

        } else {
            viewModel.getAllFromCategory(categoryType!!.name).observe(this,
                Observer<List<RecipeEntity>> { recipes -> recipeAdapter.setRecipes(recipes!!) })
        }
    }

    override fun onCategoryChanged(newCategory: CategoryType) {
        Log.d("MenuFragment", "category changed")
        setupData()
    }
}