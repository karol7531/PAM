package com.apocalypse_survivors.przepisyapp.ui.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.apocalypse_survivors.przepisyapp.R

class StepsFragment : Fragment() {

    private lateinit var viewModel: StepsViewModel
    private lateinit var playButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(StepsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_steps, container, false)

        playButton = root.findViewById(R.id.steps_play_button)
        nextButton = root.findViewById(R.id.steps_next_button)
        prevButton = root.findViewById(R.id.steps_prev_button)

        arguments?.let {
            val args = StepsFragmentArgs.fromBundle(it)
            viewModel.steps = args.steps
        }

        return root
    }
}