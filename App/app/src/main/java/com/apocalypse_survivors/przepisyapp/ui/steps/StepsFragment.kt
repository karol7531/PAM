package com.apocalypse_survivors.przepisyapp.ui.steps

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.apocalypse_survivors.przepisyapp.R
import java.util.*


class StepsFragment : Fragment() {

    private lateinit var viewModel: StepsViewModel
    private lateinit var playButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var textView: TextView
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(StepsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_steps, container, false)

        playButton = root.findViewById(R.id.steps_play_button)
        nextButton = root.findViewById(R.id.steps_next_button)
        prevButton = root.findViewById(R.id.steps_prev_button)
        textView = root.findViewById(R.id.steps_descripton)

        arguments?.let {
            val args = StepsFragmentArgs.fromBundle(it)
            viewModel.steps = args.steps
        }

        changeText()

        initTextToSpeech(Locale.US)

        playButton.setOnClickListener {
            if (!textToSpeech.isSpeaking) {
                val text = viewModel.steps[viewModel.currentStep]
                val speechStatus = textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)

                if (speechStatus == TextToSpeech.ERROR) {
                    Log.e("StepsFragment", "Error in converting Text to Speech!")
                }
            }
        }

        prevButton.setOnClickListener {
            if(viewModel.currentStep > 0){
                viewModel.currentStep--
                changeText()
                //TODO: check if necessary
                if(textToSpeech.isSpeaking){
                    textToSpeech.stop()
                }
            }
        }

        nextButton.setOnClickListener {
            if(viewModel.currentStep < viewModel.steps.size - 1){
                viewModel.currentStep++
                changeText()
                //TODO: check if necessary
                if(textToSpeech.isSpeaking){
                    textToSpeech.stop()
                }
            }
        }

        return root
    }

    private fun changeText() {
        val text = "${viewModel.currentStep + 1}. ${viewModel.steps[viewModel.currentStep]}"
        textView.text = text
    }

    private fun initTextToSpeech(loc: Locale) {
        textToSpeech = TextToSpeech(context,
            TextToSpeech.OnInitListener { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val ttsLang = textToSpeech.setLanguage(loc)

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("StepsFragment", "The Language is not supported!")
                    } else {
                        Log.i("StepsFragment", "Language Supported.")
                    }
                    Log.i("StepsFragment", "Initialization success.")
                }
//                else {
//                    Toast.makeText(
//                        context,
//                        "TTS Initialization failed!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }

    override fun onPause() {
        super.onPause()
        textToSpeech.stop()
        viewModel.currentStep = 0
    }
}