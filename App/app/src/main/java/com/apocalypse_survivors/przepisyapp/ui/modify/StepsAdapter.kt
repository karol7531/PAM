package com.apocalypse_survivors.przepisyapp.ui.modify

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apocalypse_survivors.przepisyapp.R
import com.apocalypse_survivors.przepisyapp.database.entities.StepEntity

class StepsAdapter(var stepsRecyclerView: RecyclerView) : RecyclerView.Adapter<StepsAdapter.StepsHolder>() {

    internal var steps : MutableList<StepEntity> = mutableListOf()
        set(value) {
            Log.d("StepsAdapter", "steps setted, item_len = ${steps.size}")
            field = value
            //not the best way to notify
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.step_item, parent,false)
        return StepsHolder(itemView)
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    override fun onBindViewHolder(holder: StepsHolder, position: Int) {
        var currentStep  = steps[position]

        holder.numTextView.text = "${position + 1}. "
        currentStep.number = position + 1


        holder.descEditText.setText(currentStep.description)

        holder.descEditText.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                Log.d("StepsAdapter", "text changed on position: ${position + 1}")

                currentStep.description = s.toString()

                // we are at the last element and user typed in something -> add new item to the list
                if (position == itemCount - 1){
                    addStep()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    internal fun addStep(){
        Log.i("StepsAdapter", "AddStep")
        steps.add(StepEntity())

        //NOTE: notifyDataSetChanged() not working properly with TextChangedListener
        notifyItemInserted(steps.size - 1)
        stepsRecyclerView.requestLayout()
    }

    inner class StepsHolder(view: View) : RecyclerView.ViewHolder(view){
        val numTextView : TextView = view.findViewById(R.id.step_item_num)
        val descEditText:EditText = view.findViewById(R.id.step_item_desc)
    }
}