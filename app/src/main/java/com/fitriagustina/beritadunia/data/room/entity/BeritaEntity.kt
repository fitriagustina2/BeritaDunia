package com.fitriagustina.beritadunia.data.room.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

//BeritaEntity merupakan data table untuk bookmark di room
@Entity(tableName = "berita")
@Parcelize
data class BeritaEntity(
        @ColumnInfo(name = "name") var name: String = "",
        @ColumnInfo(name = "author") var author: String = "",
        @ColumnInfo(name = "title") var title: String = "",
        @ColumnInfo(name = "description") var description: String = "",
        @ColumnInfo(name = "url") var url: String = "",
        @ColumnInfo(name = "urlToImage") var urlToImage: String = "",
        @ColumnInfo(name = "publishedAt") var publishedAt: String = "",
        @ColumnInfo(name = "content") var content: String = "",
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0
) : Parcelable