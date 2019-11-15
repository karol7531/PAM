package com.apocalypse_survivors.przepisyapp.ui.menu

import android.os.Bundle
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
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryEntity
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MenuFragment : Fragment() {

    private lateinit var viewModel: MenuViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_menu, container, false)

        fab = root.findViewById(R.id.menu_fab_modify)
        recyclerView  = root.findViewById(R.id.menu_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        val recipeAdapter = RecipeAdapter(activity!!)
        recyclerView.adapter = recipeAdapter

        recipeAdapter.setOnItemCLickListener(object : RecipeAdapter.OnItemClickListener{
            override fun onItemClick(recipe: RecipeEntity) {
                //TODO: pass arguments
                //WARN: works only on menu fragment
                Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(R.id.action_nav_menu_to_nav_recipe)
            }

        })

        //set items to recycler and observe live data
        val category = (activity as MainActivity).categorySelected
        if (category.isNullOrEmpty()){
            viewModel.getAll().observe(this,
                Observer<List<RecipeEntity>> { recipes -> recipeAdapter.setRecipes(recipes!!) })

        }else{
            viewModel.getAllFromCategory(category).observe(this,
                Observer<List<RecipeEntity>> { recipes -> recipeAdapter.setRecipes(recipes!!) })
        }

        //fab
        fab.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(R.id.action_nav_menu_to_nav_modify)
        }

        return root
    }
}