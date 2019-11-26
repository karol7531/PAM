package com.apocalypse_survivors.przepisyapp.ui.steps

import androidx.lifecycle.ViewModel

class StepsViewModel : ViewModel() {
    internal lateinit var steps : Array<String>
    internal var currentStep: Int = 0


}