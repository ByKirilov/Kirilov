package com.bykirilov.kirilovdeveloperslife.viewModel

import android.app.Application
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.bumptech.glide.Glide
import com.bykirilov.kirilovdeveloperslife.R
import com.bykirilov.kirilovdeveloperslife.model.Post
import com.bykirilov.kirilovdeveloperslife.model.PostModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    var postModel = PostModel()

    private var postNumber = -1

    val post = ObservableField(Post("", ""))

    val isLoading = ObservableField<Boolean>()

    var isPreviousEnabled = ObservableField(false)

    fun getNextPost() = launch {
        isLoading.set(true)

        post.set(withContext(Dispatchers.IO) {
            postModel.getNextPost(++postNumber)
        })
        isLoading.set(false)

        if (postNumber > 0) {
            isPreviousEnabled.set(true)
        }
    }

    fun getPreviousPost() = launch {
        if (postNumber > 0){
            isLoading.set(true)
            post.set(withContext(Dispatchers.IO) {
                postModel.getPreviousPost(--postNumber)
            })
            isLoading.set(false)
        }
        if (postNumber <= 0) {
            isPreviousEnabled.set(false)
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("gifURL")
        fun loadImage(view: ImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                val httpsUrl = "https" + url.slice(4 until url.length)
                Glide
                    .with(view.context)
                    .load(httpsUrl)
                    .placeholder(R.drawable.loading)
                    .into(view)
            }
        }
    }
}