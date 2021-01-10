package com.example.retrofitdemo.repository

import com.example.retrofitdemo.api.RetrofitInstance
import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.data.Posts
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
}