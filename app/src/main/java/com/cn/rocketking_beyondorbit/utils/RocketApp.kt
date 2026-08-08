package com.cn.rocketking_beyondorbit.utils

import android.app.Application

class RocketApp: Application() {

    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
    }
}