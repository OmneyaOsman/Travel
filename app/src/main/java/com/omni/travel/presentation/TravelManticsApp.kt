package com.omni.travel.presentation

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics

class TravelManticsApp  :Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseAnalytics.getInstance(this)

    }
}