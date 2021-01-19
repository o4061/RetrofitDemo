package com.example.retrofitdemo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitdemo.R
import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.repository.Repository
import com.example.retrofitdemo.utils.Communicator
import com.example.retrofitdemo.utils.MainActivityViewModelFactory


class CreatePostFragment : Fragment() {

    private lateinit var communicator: Communicator
    private lateinit var post: Post
    private lateinit var viewModel: MainActivityViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_show_result, container, false)
        return view
    }

}