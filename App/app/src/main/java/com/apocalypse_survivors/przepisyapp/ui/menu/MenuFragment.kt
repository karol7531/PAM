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
import com.apocalypse_survivors.przepisyapp.OnCategoryChangedListener
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryType
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.apocalypse_survivors.przepisyapp.ui.activity.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MenuFragment : Fragment(), OnCategoryChangedListener {

    private lateinit var viewModel: MenuViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeAdapter : RecipeAdapter
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_menu, container, false)

        recipeAdapter = RecipeAdapter(activity!!)
        fab = root.findViewById(R.id.menu_fab_modify)
        recyclerView  = root.findViewById(R.id.menu_recycler_view)

        //recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = recipeAdapter

        //adapter
        recipeAdapter.setOnItemCLickListener(object : RecipeAdapter.OnItemClickListener{
            override fun onItemClick(recipe: RecipeEntity, position: Int) {
                Log.i("MenuFragment", "item selected at position $position")
                viewModel.selectedRecipePosition = position
                val selectAction = MenuFragmentDirections.selectAction()
                selectAction.setRecipeId(recipe.id)
                Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(selectAction)
                Log.d("MenuFragment", "navigate to RecipeFragment")
            }
        })

        setupData()

        //fab
        fab.setOnClickListener {
            Log.i("MenuFragment", "add fab clicked")
            Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(R.id.add_action)
        }

        return root
    }

    private fun scrollRecycler(){
        Log.d("MenuFragment", "scroll to position: ${viewModel.selectedRecipePosition}")
        recyclerView.smoothScrollToPosition(viewModel.selectedRecipePosition)
    }

    //set items to recycler and observe live data
    private fun setupData() {
        //WARN: not safe activity cast
        viewModel.setupData((activity as MainActivity).getCategorySelected())

        viewModel.recipes.observe(this,
            Observer<List<RecipeEntity>> {
                if (recipeAdapter.recipes != it) {
                    recipeAdapter.recipes = it
                }
                scrollRecycler()
            })
        Log.d("MenuFragment", "data setted up")
    }

    //drawer
    override fun onCategoryChanged(newCategory: CategoryType) {
        Log.d("MenuFragment", "category changed")
        setupData()
    }
}