package com.apocalypse_survivors.przepisyapp.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.apocalypse_survivors.przepisyapp.R

class RecipeFragment : Fragment() {

    private lateinit var slideshowViewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_recipe, container, false)
//        val textView: TextView = root.findViewById(R.id.text_slideshow)
//        slideshowViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }
}