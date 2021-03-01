package com.example.retrofitdemo.data.responseData

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Posts() : ArrayList<Post>(), Parcelable
