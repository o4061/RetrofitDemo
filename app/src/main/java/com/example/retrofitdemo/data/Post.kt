package com.example.retrofitdemo.data

import java.io.Serializable

data class Post(
    val id: Int,
    val title: String,
) : Serializable