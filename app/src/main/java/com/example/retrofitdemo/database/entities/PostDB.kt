package com.example.retrofitdemo.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class PostDB(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
)
