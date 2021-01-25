package com.example.retrofitdemo.data

import android.os.Parcel
import android.os.Parcelable

class Codes() : ArrayList<Int>(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Codes> {
        override fun createFromParcel(parcel: Parcel): Codes {
            return Codes(parcel)
        }

        override fun newArray(size: Int): Array<Codes?> {
            return arrayOfNulls(size)
        }
    }
}