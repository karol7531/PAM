package com.apocalypse_survivors.przepisyapp.ui.menu

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity


class RecipeAdapter(private val activity: Activity) : RecyclerView.Adapter<RecipeAdapter.RecipeHolder>() {

    private var recipes : List<RecipeEntity> = listOf()
    private lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent,false)
        return RecipeHolder(itemView)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        var currentRecipe  = recipes[position]
        holder.nameTextView.text = currentRecipe.name

        Log.d("RecipeAdapter", "imgPath: ${currentRecipe.image}")
        if (currentRecipe.image.isNotEmpty()){
            try {
                val imgUri = Uri.parse(currentRecipe.image)
                //IDEA: Glide
                val bitmapImage : Bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, imgUri)
                holder.imageButton.setImageBitmap(bitmapImage)
            } catch (e: NullPointerException) {
                Log.w("RecipeAdapter", "image not found: ${currentRecipe.image}")
            }
        }
    }

    fun setRecipes(recipes : List<RecipeEntity>){
        Log.i("RecipeAdapter", "DataSetChanged: item_len = ${recipes.size}")
        this.recipes = recipes
        //not the best way to notify
        notifyDataSetChanged()
    }

    inner class RecipeHolder(view: View) : RecyclerView.ViewHolder(view){
        val f = view.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION){
                val selectedRecipe = recipes[adapterPosition]
                onItemClickListener.onItemClick(selectedRecipe)
            }
        }
        val nameTextView: TextView = view.findViewById(R.id.recipe_item_name)
        val imageButton: ImageView = view.findViewById(R.id.recipe_item_image)
    }

    interface OnItemClickListener{
        fun onItemClick(recipe: RecipeEntity)
    }

    fun setOnItemCLickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }
}


