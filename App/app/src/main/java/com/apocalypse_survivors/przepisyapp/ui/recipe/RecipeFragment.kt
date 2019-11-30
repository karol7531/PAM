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
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.apocalypse_survivors.przepisyapp.database.entities.StepEntity
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

//TODO: toolbar desc
class RecipeFragment : Fragment() {

    private lateinit var viewModel: RecipeViewModel
    private lateinit var image: ImageView
    private lateinit var fab: FloatingActionButton
    private lateinit var desc: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_recipe, container, false)

        image = root.findViewById(R.id.recipe_image)
        fab = root.findViewById(R.id.recipe_fab_play)
        desc = root.findViewById(R.id.recipe_descripton)

        arguments?.let {
            val args = RecipeFragmentArgs.fromBundle(it)
            viewModel.recipeId = args.recipeId
        }

        setupData()

        //fab
        fab.setOnClickListener {
            if (viewModel.steps.isNotEmpty()) {
                val stepsAction = RecipeFragmentDirections.stepsAction(arrayOf())
                stepsAction.setSteps(viewModel.steps.map {
                        step -> "${step.description}"
                }.toTypedArray())
                Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(stepsAction)
            } else {
                //TODO: string to strings
                Toast.makeText(context, "No steps for this recipe", Toast.LENGTH_SHORT).show()
            }

//            val stepsAction = RecipeFragmentDirections.stepsAction(arrayOf())
//            stepsAction.setSteps(arrayOf("test, recipe text 1", "test, recipe text 2", "test, recipe text 3"))
//            Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(stepsAction)
        }

        return root
    }

    private fun setValues() {
        // set image
        try {
            val imgUri = Uri.parse(viewModel.recipe.image)
            Glide
                .with(context!!)
                .load(imgUri)
                .into(image)
        } catch (e: Exception) {
            Log.w("RecipeAdapter", "image not found: ${viewModel.recipe.image}")
        }

        // set description
        desc.text = viewModel.getDescText()
    }

    //TODO: make it work as it should
    private fun setupData() {
        viewModel.getRecipe().observe(this,
            Observer<RecipeEntity> {recipe ->
                viewModel.recipe = recipe

                viewModel.getSteps().observe(this,
                    Observer<List<StepEntity>> { steps ->
                        viewModel.steps = steps })

                setValues()
            })
    }
}