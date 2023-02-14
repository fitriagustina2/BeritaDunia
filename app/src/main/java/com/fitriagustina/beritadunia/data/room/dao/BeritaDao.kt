package com.fitriagustina.beritadunia.data.room.dao

import androidx.room.*
import com.fitriagustina.beritadunia.data.room.entity.BeritaEntity

//BaritaDao merupakan class interface yang berisi perintah-perintah query ke room
@Dao
interface BeritaDao {
    //mengambil semua data berita
    @Query("SELECT * from berita")
    fun getBoomarks(): List<BeritaEntity>

    //mengambil 1 data berita berdasarkan title/juudl
    @Query("SELECT * from berita WHERE title=:title limit 1")
    fun getBookmark(title: String): BeritaEntity

    //menambahkan data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteEntity: BeritaEntity)

    //mengupdate data
    @Update
    fun update(favoriteEntity: BeritaEntity)

    //menghapus data berita berdasarkan title/judul
    @Query("DELETE FROM berita WHERE title=:title")
    fun delete(title: String)
}