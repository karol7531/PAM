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
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MenuFragment : Fragment() {

    private lateinit var homeViewModel: MenuViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_menu, container, false)

        val recyclerView : RecyclerView = root.findViewById(R.id.menu_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        val recipeAdapter = RecipeAdapter()
        recyclerView.adapter = recipeAdapter

        //TODO: take category name from action in navigation component
        //WARN: hardcoded value
        homeViewModel.getAllFromCategory("DESSERTS").observe(this,
            Observer<List<RecipeEntity>> { recipes -> recipeAdapter.setRecipes(recipes!!) })

        val fab : FloatingActionButton = root.findViewById(R.id.menu_fab_modify)
        fab.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(R.id.action_nav_menu_to_nav_modify)
        }

        return root
    }
}