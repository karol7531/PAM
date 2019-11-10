package com.apocalypse_survivors.przepisyapp.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity

class RecipeAdapter : RecyclerView.Adapter<RecipeHolder>() {

    private var recipes : List<RecipeEntity> = listOf()

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
    }

    fun setRecipes(recipes : List<RecipeEntity>){
        this.recipes = recipes
        //not the best way to notify
        notifyDataSetChanged()
    }
}

class RecipeHolder(view: View) : RecyclerView.ViewHolder(view){
    val nameTextView: TextView = view.findViewById(R.id.recipe_item_name)
}

