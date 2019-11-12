package com.apocalypse_survivors.przepisyapp.ui.modify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.apocalypse_survivors.przepisyapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ModifyFragment : Fragment(), AdapterView.OnItemSelectedListener{

    private lateinit var viewModel : ModifyViewModel
    private lateinit var nameEdit : EditText
    private lateinit var ingredientsEdit : EditText
    private lateinit var fab: FloatingActionButton
    private lateinit var spinner: Spinner

    private lateinit var category: String

    //IDEA: get category from menu
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(ModifyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_modify, container, false)

        spinner = root.findViewById(R.id.modify_cat_spinner)
        fab = root.findViewById(R.id.modify_fab_done)
        nameEdit = root.findViewById(R.id.modify_text_name)
        ingredientsEdit = root.findViewById(R.id.modify_text_ingredients)

        //fab
        fab.setOnClickListener {
            if(!saveRecipe()){
                Toast.makeText(context, R.string.inset_name, Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(context, R.string.save_success, Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }
        }

        //spinner
        val spinnerAdapter  = ArrayAdapter.createFromResource(context, R.array.categories, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = this




        return root
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
       category = parent?.getItemAtPosition(position).toString()
    }

    //TODO: give real values
    private fun saveRecipe() : Boolean{
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