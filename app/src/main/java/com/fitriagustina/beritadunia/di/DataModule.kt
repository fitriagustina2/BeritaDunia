package com.fitriagustina.beritadunia.di

import android.content.Context
import com.fitriagustina.beritadunia.data.room.MainRoomDatabase

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

//Module Koin untuk inisialisasi awal Room
val dataModule = module {
    single { createRoomDatabase(androidContext()) }
}

fun createRoomDatabase(context: Context) : MainRoomDatabase = MainRoomDatabase.getInstance(context)!!
