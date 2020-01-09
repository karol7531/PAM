package com.apocalypse_survivors.przepisyapp.ui.recipe

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.database.entities.StepEntity

class StepsAdapter : RecyclerView.Adapter<StepsAdapter.StepsHolder>() {

    internal var steps : MutableList<StepEntity> = mutableListOf()
        set(value) {
            Log.d("StepsAdapter", "steps setted, item_len = ${steps.size}")
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_step_item, parent,false)
        return StepsHolder(itemView)
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    override fun onBindViewHolder(holder: StepsHolder, position: Int) {
        var currentStep  = steps[position]

        holder.numTextView.text = "${position + 1}. "
        currentStep.number = position + 1

        holder.descEditText.text = currentStep.description
    }

    inner class StepsHolder(view: View) : RecyclerView.ViewHolder(view){
        val numTextView : TextView = view.findViewById(R.id.recipe_step_item_num)
        val descEditText: TextView = view.findViewById(R.id.recipe_step_item_desc)
    }
}