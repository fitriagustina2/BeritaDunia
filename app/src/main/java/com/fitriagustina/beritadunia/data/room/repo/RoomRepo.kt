package com.fitriagustina.beritadunia.data.room.repo

import com.fitriagustina.beritadunia.data.room.MainRoomDatabase
import com.fitriagustina.beritadunia.data.room.entity.BeritaEntity
import io.reactivex.Observable

//RoomRepo merupakan jembatan untuk menghubungkan ui dengan room
class RoomRepo(private val db: MainRoomDatabase) {

    //mengambil semua data bookmark
    fun getBookmarks() : Observable<List<BeritaEntity>>{
        return Observable.fromCallable { db.beritaDo().getBoomarks() }
    }

    //mengambil 1 data bookmark berdasarkan title/judul
    fun getBookmark(title: String) : Observable<BeritaEntity>{
        return Observable.fromCallable { db.beritaDo().getBookmark(title) }
    }

    //Add
    fun addBookmark(beritaEntity: BeritaEntity): Observable<Unit> {
        return Observable.fromCallable { db.beritaDo().insert(beritaEntity) }
    }

    //Delete
    fun deleteBookmark(title: String): Observable<Unit> {
        return Observable.fromCallable { db.beritaDo().delete(title) }
    }
}