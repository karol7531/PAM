package com.apocalypse_survivors.przepisyapp.ui.menu

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apocalypse_survivors.przepisyapp.OnCategoryChangedListener
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryType
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.apocalypse_survivors.przepisyapp.ui.activity.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MenuFragment : Fragment(), OnCategoryChangedListener{

    private lateinit var viewModel: MenuViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeAdapter : RecipeAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var layout: CoordinatorLayout

    companion object{
        const val UNDO_SNACKBAR_DURATION : Long = 10000
        const val HINT_SNACKBAR_DURATION : Long = 20000
        const val PREF_hintSnackbarCounter : String = "hintSnackbarCounter"
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_menu, container, false)

        recipeAdapter = RecipeAdapter(activity!!)
        fab = root.findViewById(R.id.menu_fab_modify)
        recyclerView  = root.findViewById(R.id.menu_recycler_view)
        layout = root.findViewById(R.id.menu_layout)

        //recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = recipeAdapter

        ItemTouchHelper(
            //swipe right
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    Log.i("MenuFragment", "recipe swiped right")
                    //delete item swiped
                    delayedDelete(viewHolder.adapterPosition)
                }
            }).attachToRecyclerView(recyclerView)

        //adapter
        recipeAdapter.setOnItemCLickListener(object : RecipeAdapter.OnItemClickListener{
            override fun onItemClick(recipe: RecipeEntity, position: Int) {
                Log.i("MenuFragment", "item clicked at position $position")
                viewModel.selectedRecipePosition = position
                val selectAction = MenuFragmentDirections.selectAction()
                selectAction.setRecipeId(recipe.id)
                Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(selectAction)
                Log.d("MenuFragment", "navigate to RecipeFragment")
            }
        })

        recipeAdapter.setOnLongItemClickListener(object : RecipeAdapter.OnLongItemClickListener{
            override fun onItemLongClick(recipe: RecipeEntity, position: Int) {
                Log.i("MenuFragment", "item long clicked at position $position")
                viewModel.selectedRecipePosition = position
                val addAction = MenuFragmentDirections.addAction()
                addAction.setRecipeId(recipe.id)
                Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(addAction)
                Log.d("MenuFragment", "navigate to ModifyFragment")
            }

        })

        setupData()

        //fab
        fab.setOnClickListener {
            Log.i("MenuFragment", "add fab clicked")
            Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(R.id.add_action)
            Log.d("MenuFragment", "navigate to ModifyFragment")
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        restorePreferences()

        //show snackbar with hint
        if(viewModel.hintSnackbarCounter <= 5 && viewModel.showHint){
            // snackbar with hint button
            showSnackbar(R.string.snackbar_hint, R.string.ok, {}, HINT_SNACKBAR_DURATION, true)
            viewModel.showHint = false
            Log.v("MenuFragment","$PREF_hintSnackbarCounter: ${viewModel.hintSnackbarCounter}")
            viewModel.hintSnackbarCounter ++
        }
    }

    private fun restorePreferences(){
        val settings = activity!!.getPreferences(Context.MODE_PRIVATE)

        viewModel.hintSnackbarCounter = settings.getInt(PREF_hintSnackbarCounter,0)

        Log.d("MenuFragment", "Preferences restored")
    }

    override fun onStop() {
        super.onStop()
        storePreferences()
    }

    private fun storePreferences() {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        // if we want more sharedPreferences files we can use getSharedPreferences(filename, mode)
        val settings = activity!!.getPreferences(Context.MODE_PRIVATE)
        val editor = settings.edit()

        editor.putInt(PREF_hintSnackbarCounter, viewModel.hintSnackbarCounter)
        // Apply the edits! - async
        editor.apply()

        Log.d("MenuFragment", "Preferences stored")
    }

    private fun delayedDelete(adapterPosition: Int) {
        val recipe = recipeAdapter.getRecipeAtPosition(adapterPosition)

        viewModel.delete(recipe)
        Log.d("MenuFragment", "recipe deleted ${recipe.id}")

        // snackbar with undo button
        showSnackbar(R.string.recipe_deleted, R.string.undo, {
            viewModel.reviveRecentlyDeleted()
            Snackbar.make(layout, getString(R.string.undo_successful), Snackbar.LENGTH_LONG).show()
        }, UNDO_SNACKBAR_DURATION, false)
    }

    private fun showSnackbar(descResource:Int, actionResource:Int, action: ()->Unit, duration: Long, dismissOnClick:Boolean){
        // snackbar with button
        val snackbar= Snackbar.make(layout, descResource, Snackbar.LENGTH_INDEFINITE).also {s ->
            s.setAction(actionResource, View.OnClickListener {
                Log.i("MenuFragment", "${getString(actionResource)} on snackbar clicked")
                action()
                if(dismissOnClick){
                    s.dismiss()
                }
            })
            s.show()
        }

        // dismiss after time
        Handler().postDelayed({
            if(snackbar.isShown){
                snackbar.dismiss()
                Log.d("MenuFragment", "snackbar closed -> no user response")
            }
        }, duration)
    }

    private fun scrollRecycler(){
        Log.d("MenuFragment", "scroll to position: ${viewModel.selectedRecipePosition}")
        recyclerView.smoothScrollToPosition(viewModel.selectedRecipePosition)
    }

    //set items to recycler and observe live data
    private fun setupData() {
        //WARN: not safe activity cast
        viewModel.setupData((activity as MainActivity).getCategorySelected())
        //WARN: activity cast
        (activity as MainActivity).setToolbarTitle()

        viewModel.recipes.removeObservers(activity!!)

        //BUG: live data sometimes observe something it shouldn't be observing -> wrong items are observed
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