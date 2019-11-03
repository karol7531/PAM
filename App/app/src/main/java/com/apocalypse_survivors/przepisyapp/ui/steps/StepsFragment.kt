package com.apocalypse_survivors.przepisyapp.ui.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.apocalypse_survivors.przepisyapp.R

class StepsFragment : Fragment() {

    private lateinit var toolsViewModel: StepsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toolsViewModel =
            ViewModelProviders.of(this).get(StepsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_steps, container, false)
//        val textView: TextView = root.findViewById(R.id.text_tools)
//        toolsViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }
}