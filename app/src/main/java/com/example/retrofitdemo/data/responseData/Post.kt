package com.example.retrofitdemo.data.responseData

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    val id: Int,
    val title: String?,
) : Parcelable