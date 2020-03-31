package com.datingapp.mobile

import android.app.Application
import com.facebook.soloader.SoLoader


class DatingAppApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)
    }
}