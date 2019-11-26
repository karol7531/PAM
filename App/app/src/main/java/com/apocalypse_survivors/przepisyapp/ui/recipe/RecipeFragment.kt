package com.apocalypse_survivors.przepisyapp.ui.recipe

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.apocalypse_survivors.przepisyapp.database.entities.StepEntity
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

        //fab
        fab.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(R.id.steps_action)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("RecipeFragment", "onViewCreated()")
        super.onViewCreated(view, savedInstanceState)

        //IDEA: in onCreateView
        //get recipe
        arguments?.let {
            val args = RecipeFragmentArgs.fromBundle(it)
            viewModel.recipeId = args.recipeId
        }

        setupData()
    }

    private fun setValues() {
        // set image
        try {
            val imgUri = Uri.parse(viewModel.recipe.image)
            //IDEA: Glide
            val bitmapImage: Bitmap =
                MediaStore.Images.Media.getBitmap(activity?.contentResolver, imgUri)
            image.setImageBitmap(bitmapImage)
        } catch (e: Exception) {
            Log.w("RecipeAdapter", "image not found: ${viewModel.recipe.image}")
        }

        // set description
        desc.text = viewModel.getDescText()
    }

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