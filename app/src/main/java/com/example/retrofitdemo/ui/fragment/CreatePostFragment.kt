package com.example.retrofitdemo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitdemo.R
import com.example.retrofitdemo.data.responseData.Post
import com.example.retrofitdemo.repository.Repository
import com.example.retrofitdemo.ui.activity.startPage.StartPageViewModel
import com.example.retrofitdemo.utils.Communicator
import com.example.retrofitdemo.utils.StartPageViewModelFactory
import com.example.retrofitdemo.data.enums.RequestType
import kotlinx.android.synthetic.main.fragment_create_post.view.*

class CreatePostFragment : Fragment() {
    private lateinit var resultPost: Post
    private var resultCode: Int = 0
    private lateinit var errorMessage: String

    private lateinit var communicator: Communicator
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: StartPageViewModelFactory
    private lateinit var viewModel: StartPageViewModel


    companion object {
        private val fragment = CreatePostFragment()
        fun newInstance(requestType: RequestType): CreatePostFragment {
            val bundle = Bundle().apply {
                putString("TYPE", requestType.toString())
            }

            fragment.arguments = bundle
            return fragment
        }

        fun getArgs() =
            fragment.arguments?.getString("TYPE")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communicator = activity as Communicator
        repository = Repository()
        viewModelFactory = StartPageViewModelFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(StartPageViewModel::class.java)

        val requestType = getArgs()

        if (requestType == RequestType.DELETE.toString() || requestType == RequestType.POST.toString()) {
            view.postTitleInput.visibility = View.INVISIBLE
        }

        view.btnSendRequest.setOnClickListener {
            if (check(view)) {
                val id = view.postIdInput.text.toString().toInt()
                val title = view.postTitleInput.text.toString()

                when (requestType) {
                    RequestType.DELETE.toString() -> {
                        deleteRequest(Post(id, title))
                    }

                    RequestType.PATCH.toString() -> {
                        patchRequest(Post(id, title))
                    }

                    RequestType.UPDATE.toString() -> {
                        updateRequest(Post(id, title))
                    }
                    RequestType.UPLOAD.toString() -> {
                        uploadRequest(Post(id, title))
                    }
                    RequestType.POST.toString() -> {
                        postRequest(Post(id, title))
                    }
                }
            }
        }
    }

    private fun postRequest(post: Post) {
        viewModel.getPost(post.id)

        viewModel.postResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    resultPost = Post(it.id, it.title)
                    resultCode = response.code()
                }

                communicator.sendPostToShowResultFragment(resultPost, resultCode)
            } else {
                errorMessage = response.errorBody().toString()
                resultCode = response.code()

                communicator.sendErrorToShowResultFragment(errorMessage, resultCode)
            }
        })
    }

    private fun uploadRequest(post: Post) {
        viewModel.uploadPost(post)

        viewModel.postResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    resultPost = Post(it.id, it.title)
                    resultCode = response.code()
                }

                communicator.sendPostToShowResultFragment(resultPost, resultCode)
            } else {
                errorMessage = response.errorBody().toString()
                resultCode = response.code()

                communicator.sendErrorToShowResultFragment(errorMessage, resultCode)
            }
        })
    }

    private fun updateRequest(post: Post) {
        viewModel.updatePost(post)

        viewModel.postResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    resultPost = Post(it.id, it.title)
                    resultCode = response.code()
                }

                communicator.sendPostToShowResultFragment(resultPost, resultCode)
            } else {
                errorMessage = response.errorBody().toString()
                resultCode = response.code()

                communicator.sendErrorToShowResultFragment(errorMessage, resultCode)
            }
        })
    }

    private fun deleteRequest(post: Post) {
        viewModel.deletePost(post.id)

        viewModel.postResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                resultPost = Post(post.id, "The post deleted")
                resultCode = response.code()

                communicator.sendPostToShowResultFragment(resultPost, resultCode)
            } else {
                errorMessage = response.errorBody().toString()
                resultCode = response.code()

                communicator.sendErrorToShowResultFragment(errorMessage, resultCode)
            }
        })
    }

    private fun patchRequest(post: Post) {
        viewModel.patchPost(post)

        viewModel.postResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    resultPost = Post(it.id, it.title)
                    resultCode = response.code()
                }

                communicator.sendPostToShowResultFragment(resultPost, resultCode)
            } else {
                errorMessage = response.errorBody().toString()
                resultCode = response.code()

                communicator.sendErrorToShowResultFragment(errorMessage, resultCode)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_post, container, false)
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