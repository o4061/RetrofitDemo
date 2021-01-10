package com.example.retrofitdemo.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitdemo.repository.Repository
import com.example.retrofitdemo.ui.MainActivityViewModel

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repository) as T
    }

}