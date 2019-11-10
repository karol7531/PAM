package com.apocalypse_survivors.przepisyapp.ui.modify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.apocalypse_survivors.przepisyapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ModifyFragment : Fragment() {

    private lateinit var viewModel : ModifyViewModel
    private lateinit var nameEdit : EditText
    private lateinit var ingredientsEdit : EditText
    private lateinit var fab: FloatingActionButton

    //IDEA: get category from menu
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(ModifyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_modify, container, false)

        fab = root.findViewById(R.id.modify_fab_done)
        nameEdit = root.findViewById(R.id.modify_text_name)
        ingredientsEdit = root.findViewById(R.id.modify_text_ingredients)


        fab.setOnClickListener {
            if(!saveRecipe()){
                Toast.makeText(context, R.string.inset_name, Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(context, R.string.save_success, Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }
        }

        return root
    }

    //TODO: give real values
    private fun saveRecipe() : Boolean{
        //WARN: hardcoded value
        val category = "DESSERTS"
//        val category = "tego nie ma"
        val name = nameEdit.text.toString()
        val ingredients = ingredientsEdit.text.toString()

        if (name.trim().isEmpty()){
            return false
        }

        return viewModel.saveRecipe(category, category, name, ingredients, "", 10, 2)
//        viewModel.saveCategory(name, "opis")
//        return true
    }
}