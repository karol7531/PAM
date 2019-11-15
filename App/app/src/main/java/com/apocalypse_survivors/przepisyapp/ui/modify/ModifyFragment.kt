package com.apocalypse_survivors.przepisyapp.ui.modify

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.apocalypse_survivors.przepisyapp.MainActivity
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryType
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ModifyFragment : Fragment(), AdapterView.OnItemSelectedListener{

    private lateinit var viewModel : ModifyViewModel
    private lateinit var nameEdit : EditText
    private lateinit var ingredientsEdit : EditText
    private lateinit var fab: FloatingActionButton
    private lateinit var spinner: Spinner
    private lateinit var imageButton: ImageButton

    private lateinit var spinnerCategory: String
    private var imagePath : String = ""

    private val IMG_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001

    //IDEA: get spinnerCategory from menu
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(ModifyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_modify, container, false)

        imageButton = root.findViewById(R.id.modify_imageButton_add)
        spinner = root.findViewById(R.id.modify_cat_spinner)
        fab = root.findViewById(R.id.modify_fab_done)
        nameEdit = root.findViewById(R.id.modify_text_name)
        ingredientsEdit = root.findViewById(R.id.modify_text_ingredients)

        //fab
        fab.setOnClickListener {
            if(!saveRecipe()){
                Toast.makeText(context, R.string.invalid_params, Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(context, R.string.save_success, Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }
        }

        //spinner
        val categoryLabels = CategoryType.values()
            .filter { it.isMainCategory }
            .map { it.getLabel(context!!) }
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, categoryLabels)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = this

        //image button
        imageButton.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),PERMISSION_CODE)
            } else {
                pickFromGallery()
            }
        }

        return root
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
       spinnerCategory = parent?.getItemAtPosition(position).toString()
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMG_PICK_CODE)
    }

    override fun onActivityResult(requestCode:Int, resultCode: Int, intent: Intent)
    {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMG_PICK_CODE) {
                val returnUri = intent.data
                //IDEA: Glide
                val bitmapImage : Bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, returnUri)
                imageButton.setImageBitmap(bitmapImage)

                imagePath = returnUri.path

                Log.i("ModifyFragment", "img path: $imagePath")
                Log.i("ModifyFragment", "uri: ${returnUri.toString()}")
//                Log.i("ModifyFragment", "uri: ${returnUri)}")
//                Log.i("ModifyFragment", "inputStream: ${context!!.contentResolver.openInputStream(returnUri)}")


            }
        }
    }

    private fun saveRecipe() : Boolean{
        val name = nameEdit.text.toString()
        val ingredients = ingredientsEdit.text.toString()

        val dbCategory = findCategory(spinnerCategory)

        if (name.trim().isEmpty() || dbCategory.isNullOrEmpty()){
            return false
        }

        return viewModel.saveRecipe(dbCategory, dbCategory, name, ingredients, imagePath, 10, 2)
    }

    private fun findCategory(spinnerCategory: String): String {
        CategoryType.values().forEach {
            if(it.isMainCategory && it.getLabel(context!!) == spinnerCategory)
                return it.name
        }
        return ""
    }
}