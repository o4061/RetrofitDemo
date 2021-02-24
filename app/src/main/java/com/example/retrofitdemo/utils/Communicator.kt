package com.example.retrofitdemo.utils

import com.example.retrofitdemo.data.responseData.Codes
import com.example.retrofitdemo.data.responseData.Post
import com.example.retrofitdemo.data.responseData.Posts
import com.example.retrofitdemo.data.enums.RequestType

interface Communicator {
    fun sendPostToShowResultFragment(post: Post, code: Int)
    fun sendErrorToShowResultFragment(errorMessage: String, code: Int)
    fun sendPostsToShowResultFragment(posts: Posts, codes: Codes)
    fun callCreatePostFragment(requestType: RequestType)
}