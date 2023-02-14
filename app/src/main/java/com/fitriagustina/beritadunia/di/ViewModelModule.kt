package com.fitriagustina.beritadunia.di

import com.fitriagustina.beritadunia.ui.berita.BeritaViewModel
import com.fitriagustina.beritadunia.ui.bookmark.BookmarkViewModel
import com.fitriagustina.beritadunia.ui.detail_berita.DetailBeritaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

//Module Koin untuk inisialisasi awal untuk view model
val viewModelModule = module {
    viewModel { BeritaViewModel(get()) }
    viewModel { DetailBeritaViewModel(get()) }
    viewModel { BookmarkViewModel(get()) }
}