package com.example.retrofitdemo.ui.activity.startPage

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.data.Posts
import com.example.retrofitdemo.database.entities.PostDB
import com.example.retrofitdemo.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class StartPageViewModel(private val repository: Repository) : ViewModel() {

    lateinit var postsResponse: MutableLiveData<Response<Posts>>
    lateinit var postResponse: MutableLiveData<Response<Post>>
    lateinit var postsDb: MutableLiveData<Posts>

    fun getPost(postId: Int) {
        postResponse = MutableLiveData()
        viewModelScope.launch {
            val response = repository.getPost(postId)
            postResponse.value = response
        }
    }

    fun getPosts() {
        postsResponse = MutableLiveData()
        viewModelScope.launch {
            val response = repository.getPosts()
            postsResponse.value = response
        }
    }

    fun uploadPost(post: Post) {
        postResponse = MutableLiveData()
        viewModelScope.launch {
            val response = repository.uploadPost(post)
            postResponse.value = response
        }
    }

    fun updatePost(post: Post) {
        postResponse = MutableLiveData()
        viewModelScope.launch {
            val response = repository.updatePost(post)
            postResponse.value = response
        }
    }

    fun deletePost(postId: Int) {
        postResponse = MutableLiveData()
        viewModelScope.launch {
            val response = repository.deletePost(postId)
            postResponse.value = response
        }
    }

    fun patchPost(post: Post) {
        postResponse = MutableLiveData()
        viewModelScope.launch {
            val response = repository.patchPost(post)
            postResponse.value = response
        }
    }

    fun getPostsFromDb(context: Context) {
        postsDb = MutableLiveData()
        viewModelScope.launch {
            val response = repository.getPostsFromDb(context)
            val curPosts = Posts()
            response.forEach {
                curPosts.add(Post(it.id, it.title))
            }
            postsDb.value = curPosts
        }
    }

    fun addPosts(context: Context, postsDb: List<PostDB> ) {
        viewModelScope.launch {
            repository.addPostsToDb(context, postsDb)
        }
    }
}