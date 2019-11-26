package com.apocalypse_survivors.przepisyapp.ui.modify

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.apocalypse_survivors.przepisyapp.R
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ModifyFragment : Fragment(), AdapterView.OnItemSelectedListener{

    companion object{
        private const val IMG_PICK_CODE = 1000
        private const val PERMISSION_CODE_READ = 1001
    }

    private lateinit var viewModel : ModifyViewModel
    private lateinit var nameEdit : EditText
    private lateinit var ingredientsEdit : EditText
    private lateinit var fab: FloatingActionButton
    private lateinit var spinner: Spinner
    private lateinit var imageButton: ImageButton


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
            val name = nameEdit.text.toString()
            val ingredients = ingredientsEdit.text.toString()
            if(!viewModel.saveRecipe(name, ingredients, 0, 0, context!!)){
                Toast.makeText(context, R.string.invalid_params, Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(context, R.string.save_success, Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }
        }

        //spinner
        val spinnerAdapter = viewModel.getSpinnerAdapter(context!!)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = this

        //image button
        imageButton.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),PERMISSION_CODE_READ)
            } else {
                pickFromGallery()
            }
        }

        return root
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.spinnerCategory = parent?.getItemAtPosition(position).toString()
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, IMG_PICK_CODE)
    }

    override fun onActivityResult(requestCode:Int, resultCode: Int, intent: Intent) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMG_PICK_CODE) {
                val imgUri = intent.data
                Glide
                    .with(context!!)
                    .load(imgUri)
                    .into(imageButton)

                viewModel.imagePath = imgUri.toString()

                Log.i("ModifyFragment", "img path: $viewModel.imagePath")
            }
        }
    }
}