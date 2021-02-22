package com.example.retrofitdemo.repository

import android.content.Context
import com.example.retrofitdemo.api.RetrofitInstance
import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.data.Posts
import com.example.retrofitdemo.database.PostDatabase
import com.example.retrofitdemo.database.entities.PostDB
import retrofit2.Response

class Repository {
    suspend fun getPost(postId: Int): Response<Post> {
        return RetrofitInstance.API.getPost(postId)
    }

    suspend fun getPosts(): Response<Posts> {
        return RetrofitInstance.API.getPosts()
    }

    suspend fun uploadPost(post: Post): Response<Post> {
        return RetrofitInstance.API.uploadPost(post)
    }

    suspend fun updatePost(post: Post): Response<Post> {
        return RetrofitInstance.API.updatePost(post.id, post)
    }

    suspend fun deletePost(postId: Int): Response<Post> {
        return RetrofitInstance.API.deletePost(postId)
    }

    suspend fun patchPost(post: Post): Response<Post> {
        return RetrofitInstance.API.patchPost(post.id, post)
    }

    suspend fun getPostsFromDb(context: Context): List<PostDB> {
        return PostDatabase.getInstance(context).postDao().getPostsFromDb()
    }

    suspend fun addPostsToDb(context: Context, posts: List<PostDB>) {
        return PostDatabase.getInstance(context).postDao().addPosts(posts)
    }
}