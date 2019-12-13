package com.apocalypse_survivors.przepisyapp.ui.menu

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.bumptech.glide.Glide


class RecipeAdapter(private val activity: Activity) : RecyclerView.Adapter<RecipeAdapter.RecipeHolder>() {

    internal var recipes : List<RecipeEntity> = listOf()
        set(value) {
            Log.d("RecipeAdapter", "recipes setted, item_len = ${recipes.size}")
            field= value
            //not the best way to notify
            notifyDataSetChanged()
        }
    private lateinit var onItemClickListener: OnItemClickListener
    private lateinit var onLongItemClickListener: OnLongItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent,false)
        return RecipeHolder(itemView)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        val currentRecipe  = recipes[position]

        //name
        holder.nameTextView.text = currentRecipe.name

        //image
        Log.v("RecipeAdapter", "imgPath: ${currentRecipe.image}")
        try {
            val imgUri = Uri.parse(currentRecipe.image)
            Glide
                .with(activity)
                .load(imgUri)
                .centerCrop()
                .placeholder(R.drawable.ic_fast_food)
                .into(holder.imageButton)
        } catch (e: NullPointerException) {
            Log.w("RecipeAdapter", "image not found: ${currentRecipe.image}")
        }
    }

    inner class RecipeHolder(view: View) : RecyclerView.ViewHolder(view){
        val f = view.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION){
                val selectedRecipe = getRecipeAtPosition(adapterPosition)
                onItemClickListener.onItemClick(selectedRecipe, adapterPosition)
            }
        }
        val c = view.setOnLongClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION){
                val selectedRecipe = getRecipeAtPosition(adapterPosition)
                onLongItemClickListener.onItemLongClick(selectedRecipe, position = adapterPosition)
            }
            return@setOnLongClickListener true
        }
        val nameTextView: TextView = view.findViewById(R.id.recipe_item_name)
        val imageButton: ImageView = view.findViewById(R.id.recipe_item_image)
    }

    internal fun getRecipeAtPosition(position : Int) : RecipeEntity{
        return recipes[position]
    }

    internal interface OnItemClickListener{
        fun onItemClick(recipe: RecipeEntity, position: Int)
    }

    internal fun setOnItemCLickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }

    internal interface OnLongItemClickListener {
        fun onItemLongClick(recipe:RecipeEntity, position: Int)
    }

    internal fun setOnLongItemClickListener(listener: OnLongItemClickListener){
        this.onLongItemClickListener = listener
    }
}


