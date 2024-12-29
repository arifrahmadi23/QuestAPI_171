package com.example.pertemuan13

import android.app.Application
import com.example.pertemuan13.dependeciesinjection.AppContainer
import com.example.pertemuan13.dependeciesinjection.MahasiswaContainer

class MahasiswaApplications:Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = MahasiswaContainer()
    }
}