package com.fitriagustina.beritadunia

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.fitriagustina.beritadunia.di.dataModule
import com.fitriagustina.beritadunia.di.networkModule
import com.fitriagustina.beritadunia.di.repositoryModule
import com.fitriagustina.beritadunia.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

//BaseApp merupakan class inti dari aplikasi
class BaseApp: Application() {
    override fun onCreate() {
        super.onCreate()

        //memulai koin
        startKoin {
            androidContext(applicationContext) //mendapatkan context dari application
            androidLogger(Level.ERROR) //untuk menampilkan Log
            modules(listOf(dataModule, networkModule, repositoryModule, viewModelModule)) //mendaftarkan module ke koin
        }

        //menonaktifkan night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}