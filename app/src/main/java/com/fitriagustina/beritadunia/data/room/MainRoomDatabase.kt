package com.fitriagustina.beritadunia.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fitriagustina.beritadunia.data.room.dao.BeritaDao
import com.fitriagustina.beritadunia.data.room.entity.BeritaEntity
import com.fitriagustina.beritadunia.utils.Converter

//Class utama Room Database dengan memiliki 1 entity yaitu BeritaEntity
@Database(
        entities = [BeritaEntity::class],
        version = 1,
        exportSchema = false)
@TypeConverters(Converter::class)
abstract class MainRoomDatabase : RoomDatabase() {

    abstract fun beritaDo(): BeritaDao

    companion object {
        private var INSTANCE: MainRoomDatabase? = null
        //inisialisasi room database
        fun getInstance(context: Context): MainRoomDatabase?{

            if (INSTANCE == null) {
                synchronized(MainRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            MainRoomDatabase::class.java, "beritadunia")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE
        }
    }
}