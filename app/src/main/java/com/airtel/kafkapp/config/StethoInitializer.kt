package com.airtel.kafkapp.config

import android.app.Application
import com.airtel.data.data.config.initializers.AppInitializer
import com.facebook.stetho.Stetho

class StethoInitializer : AppInitializer {
    override fun init(application: Application) {
        Stetho.initializeWithDefaults(application)
    }
}
