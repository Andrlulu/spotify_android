package com.laioffer.spotify

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// indicating an entrance for Hilt for it to code generation
// for the dependencies injection
@HiltAndroidApp
class MainApplication: Application() {
}