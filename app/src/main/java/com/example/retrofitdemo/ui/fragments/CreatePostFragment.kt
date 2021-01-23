package com.example.retrofitdemo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitdemo.R
import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.repository.Repository
import com.example.retrofitdemo.ui.MainActivityViewModel
import com.example.retrofitdemo.utils.Communicator
import com.example.retrofitdemo.utils.MainActivityViewModelFactory
import com.example.retrofitdemo.utils.RequestType
import kotlinx.android.synthetic.main.fragment_create_post.view.*

class CreatePostFragment : Fragment() {
    private lateinit var resultPost: Post
    private var resultCode: Int = 0
    private lateinit var errorMessage: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val repository = Repository()
        val viewModelFactory = MainActivityViewModelFactory(repository)

        val communicator = activity as Communicator
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_create_post, container, false)
        val requestType = arguments?.getString("TYPE")

        if (requestType == RequestType.DELETE.toString() || requestType == RequestType.POST.toString()) {
            view.postTitleInput.visibility = View.INVISIBLE
        }

        view.btnSendRequest.setOnClickListener {
            if (check(view)) {
                val id = view.postIdInput.text.toString().toInt()
                val title = view.postTitleInput.text.toString()

                when (requestType) {
                    RequestType.DELETE.toString() -> {
                        viewModel.deletePost(id)

                        viewModel.postResponse.observe(viewLifecycleOwner, { response ->
                            if (response.isSuccessful) {
                                resultPost = Post(id, "The post deleted")
                                resultCode = response.code()

                                communicator.sendPostToShowResultFragment(resultPost, resultCode)
                            } else {
                                errorMessage = response.errorBody().toString()
                                resultCode = response.code()

                                communicator.sendErrorToShowResultFragment(errorMessage, resultCode)
                            }
                        })
                    }

                    RequestType.PATCH.toString() -> {
                        viewModel.patchPost(Post(id, title))

                        viewModel.postResponse.observe(viewLifecycleOwner, { response ->
                            if (response.isSuccessful) {
                                resultPost = Post(response.body()!!.id, response.body()!!.title)
                                resultCode = response.code()

                                communicator.sendPostToShowResultFragment(resultPost, resultCode)
                            } else {
                                errorMessage = response.errorBody().toString()
                                resultCode = response.code()

                                communicator.sendErrorToShowResultFragment(errorMessage, resultCode)
                            }
                        })
                    }

                    RequestType.UPDATE.toString() -> {
                        viewModel.updatePost(Post(id, title))

                        viewModel.postResponse.observe(viewLifecycleOwner, { response ->
                            if (response.isSuccessful) {
                                resultPost = Post(response.body()!!.id, response.body()!!.title)
                                resultCode = response.code()

                                communicator.sendPostToShowResultFragment(resultPost, resultCode)
                            } else {
                                errorMessage = response.errorBody().toString()
                                resultCode = response.code()

                                communicator.sendErrorToShowResultFragment(errorMessage, resultCode)
                            }
                        })
                    }
                    RequestType.UPLOAD.toString() -> {
                        viewModel.uploadPost(Post(id, title))

                        viewModel.postResponse.observe(viewLifecycleOwner, { response ->
                            if (response.isSuccessful) {
                                resultPost = Post(response.body()!!.id, response.body()!!.title)
                                resultCode = response.code()

                                communicator.sendPostToShowResultFragment(resultPost, resultCode)
                            } else {
                                errorMessage = response.errorBody().toString()
                                resultCode = response.code()

                                communicator.sendErrorToShowResultFragment(errorMessage, resultCode)
                            }
                        })

                    }
                    RequestType.POST.toString() -> {
                        viewModel.getPost(id)

                        viewModel.postResponse.observe(viewLifecycleOwner, { response ->
                            if (response.isSuccessful) {
                                resultPost = Post(response.body()!!.id, response.body()!!.title)
                                resultCode = response.code()

                                communicator.sendPostToShowResultFragment(resultPost, resultCode)
                            } else {
                                errorMessage = response.errorBody().toString()
                                resultCode = response.code()

                                communicator.sendErrorToShowResultFragment(errorMessage, resultCode)
                            }
                        })
                    }
                }
            }
        }
        return view
    }

    private fun check(view: View): Boolean {
        if (view.postTitleInput.isInvisible && view.postIdInput.text.isNotEmpty()) {
            return true
        } else if (view.postIdInput.text.isNotEmpty() && view.postTitleInput.text.isNotEmpty()) {
            return true
        }
        return false
    }
}