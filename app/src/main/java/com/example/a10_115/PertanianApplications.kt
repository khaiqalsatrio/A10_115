package com.example.a10_115

import android.app.Application
import com.example.a10_115.repository.AppContainer
import com.example.a10_115.repository.PekerjaContainer

class PertanianApplications : Application() {
    lateinit var  container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = PekerjaContainer()
    }
}