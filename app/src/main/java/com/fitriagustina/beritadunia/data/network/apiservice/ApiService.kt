package com.fitriagustina.beritadunia.data.network.apiservice

import com.fitriagustina.beritadunia.data.network.model.BeritaModel
import retrofit2.Response
import retrofit2.http.*

//ApiService merupakan class interface berisi kumpulan request ke REST API
interface ApiService {
    @GET("everything")
    suspend fun getBerita(
        @Query("q") q: String = "world",
        @Query("apiKey") apiKey: String = "f5238cfebfc24caf8fc30c5f612551c6",
        @Query("sortBy") sortBy: String = "publishedAt"
    ): Response<BeritaModel>
}