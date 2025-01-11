package com.example.pertemuan14

import android.app.Application
import com.example.pertemuan14.di.MahasiswaContainer

class MahasiswaApp : Application() {
    //fungsinya untuk menyimpan instance ContainerApp
    lateinit var containerApp: MahasiswaContainer

    override fun onCreate() {
        super.onCreate()
        // Membuat instance ContainerApp
        containerApp = MahasiswaContainer(this)
        //Instance adalah object yang dibuat dari class
    }
}