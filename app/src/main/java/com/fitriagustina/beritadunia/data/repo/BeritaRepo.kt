package com.fitriagustina.beritadunia.data.repo

import com.fitriagustina.beritadunia.data.network.apiservice.ApiService
import com.google.gson.JsonObject

//BeritaRepo merupakan jembatan untuk menghubungkan ui dengan retrofit
class BeritaRepo (private val apiService: ApiService){

    suspend fun getBerita() = apiService.getBerita()
}

