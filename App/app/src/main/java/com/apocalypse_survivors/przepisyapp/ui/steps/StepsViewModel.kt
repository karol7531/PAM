package com.apocalypse_survivors.przepisyapp.ui.steps

import android.app.Application
import androidx.lifecycle.AndroidViewModel


class StepsViewModel(application: Application) : AndroidViewModel(application) {
    internal lateinit var steps : Array<String>
    internal var currentStep: Int = 0



}