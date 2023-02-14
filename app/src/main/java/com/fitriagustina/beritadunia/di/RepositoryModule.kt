package com.fitriagustina.beritadunia.di

import com.fitriagustina.beritadunia.data.repo.BeritaRepo
import com.fitriagustina.beritadunia.data.room.repo.RoomRepo
import org.koin.dsl.module

//Module Koin untuk inisialisasi awal untuk Repo
val repositoryModule = module {
    factory { BeritaRepo(get()) }
    factory { RoomRepo(get()) }
}
