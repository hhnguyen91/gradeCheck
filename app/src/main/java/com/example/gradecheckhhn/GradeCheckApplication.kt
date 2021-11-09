package com.example.gradecheckhhn

import android.app.Application

class GradeCheckApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GradeCheckRepository.initialize(this)
    }
}