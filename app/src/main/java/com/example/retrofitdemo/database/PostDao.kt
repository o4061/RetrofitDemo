package com.example.retrofitdemo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofitdemo.entities.PostDB

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPosts(posts: List<PostDB>)

    @Query("SELECT id, title FROM post_table WHERE id = :id")
    suspend fun getPostFromDb(id: Int): PostDB

    @Query("SELECT * FROM post_table")
    suspend fun getPostsFromDb(): List<PostDB>
}