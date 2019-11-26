package com.apocalypse_survivors.przepisyapp.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.database.entities.StepEntity

//TODO: toolbar desc
class RecipeFragment : Fragment() {

    private lateinit var viewModel: RecipeViewModel
    private lateinit var image: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_recipe, container, false)

        image = root.findViewById(R.id.recipe_image)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("RecipeFragment", "onViewCreated()")
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val args = RecipeFragmentArgs.fromBundle(it)
            viewModel.setRecipe(args.recipeId)
        }

        setupData()
    }

    private fun setupData() {
        viewModel.getSteps().observe(this,
            Observer<List<StepEntity>> { steps -> viewModel.setSteps(steps) })
    }
}