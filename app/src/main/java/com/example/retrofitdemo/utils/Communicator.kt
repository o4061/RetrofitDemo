package com.example.retrofitdemo.utils

import com.example.retrofitdemo.data.Codes
import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.data.Posts

interface Communicator {
    fun sendPostToShowResultFragment(post: Post, code: Int)
    fun sendErrorToShowResultFragment(errorMessage: String, code: Int)
    fun sendPostsToShowResultFragment(posts: Posts, codes: Codes)
    fun callCreatePostFragment(requestType: RequestType)
}