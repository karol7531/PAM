package com.apocalypse_survivors.przepisyapp.ui.recipe

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.apocalypse_survivors.przepisyapp.database.entities.StepEntity
import com.apocalypse_survivors.przepisyapp.ui.activity.MainActivity
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecipeFragment : Fragment() {

    private lateinit var viewModel: RecipeViewModel
    private lateinit var image: ImageView
    private lateinit var fab: FloatingActionButton
    private lateinit var desc: TextView
    private lateinit var stepsRecyclerView: RecyclerView
    private lateinit var stepsAdapter: StepsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_recipe, container, false)

        image = root.findViewById(R.id.recipe_image)
        fab = root.findViewById(R.id.recipe_fab_play)
        desc = root.findViewById(R.id.recipe_description)
        stepsRecyclerView = root.findViewById(R.id.recipe_steps_recyclerview)
        stepsAdapter = StepsAdapter()

        //stepsRecyclerView
        stepsRecyclerView.layoutManager = LinearLayoutManager(context)
        stepsRecyclerView.setHasFixedSize(true)
        stepsRecyclerView.adapter = stepsAdapter
        stepsRecyclerView.isNestedScrollingEnabled = false

        getNavigationArguments()

        setupData()

        //fab
        fab.setOnClickListener {
            Log.i("RecipeFragment", "fab clicked")
            if (viewModel.steps.isNotEmpty()) {
                val stepsAction = RecipeFragmentDirections.stepsAction(arrayOf(), viewModel.recipe.name)
                stepsAction.setSteps(viewModel.steps.map {
                        step -> "${step.description}"
                }.toTypedArray())
                Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(stepsAction)
            } else {
                Toast.makeText(context, "No steps for this recipe", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun setToolbarTitle() {
        //WARN: activity cast
        (activity as MainActivity).setToolbarTitle(viewModel.recipe.name)
    }

    // gets passed arguments from bundle
    private fun getNavigationArguments() {
        arguments?.let {
            val args = RecipeFragmentArgs.fromBundle(it)
            viewModel.recipeId = args.recipeId
        }
    }

    private fun setImage() {
        try {
            val imgUri = Uri.parse(viewModel.recipe.image)
            Glide
                .with(context!!)
                .load(imgUri)
                .centerCrop()
                .placeholder(R.drawable.ic_fast_food)
                .into(image)
            Log.d("RecipeFragment", "image setted")
        } catch (e: Exception) {
            Log.w("RecipeFragment", "image not found: ${viewModel.recipe.image}")
        }
    }

    private fun setDesc(){
        desc.text = viewModel.getDescText()
        desc.text = viewModel.recipe.description

        if (viewModel.steps.isNotEmpty()) {
            stepsAdapter.steps = viewModel.steps as MutableList<StepEntity>
        }

        Log.d("RecipeFragment", "description setted")
//        Log.v("RecipeFragment", "desc: ${desc.text}")
    }

    //TODO: make it work as it should -> no getRecipe()
    private fun setupData() {
        viewModel.getRecipe().observe(this,
            Observer<RecipeEntity> {recipe ->
                viewModel.recipe = recipe

                viewModel.getSteps().observe(this,
                    Observer<List<StepEntity>> { steps ->
                        viewModel.steps = steps
                        setToolbarTitle()
                        setDesc()
                    })

                setImage()
            })
    }
}