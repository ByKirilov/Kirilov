package com.bykirilov.kirilovdeveloperslife.viewModel

import android.app.Application
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.bumptech.glide.Glide
import com.bykirilov.kirilovdeveloperslife.R
import com.bykirilov.kirilovdeveloperslife.managers.NetManager
import com.bykirilov.kirilovdeveloperslife.model.NetworkStatus
import com.bykirilov.kirilovdeveloperslife.model.Post
import com.bykirilov.kirilovdeveloperslife.model.PostModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    var postModel = PostModel(NetManager(getApplication()))

    private var postNumber = -1

    val post = ObservableField(Post("", ""))

    val isLoading = ObservableField<Boolean>()

    val isPreviousEnabled = ObservableField(false)

    val isPreviousVisible = ObservableField(true)
    val isNextVisible = ObservableField(true)
    val isRefreshVisible = ObservableField(false)

    fun getNextPost() = launch {
        isLoading.set(true)

        post.set(withContext(Dispatchers.IO) {
            postModel.getNextPost(++postNumber)
        })
        if (post.get()?.gifURL == NetworkStatus.NO_INTERNET_CONNECTION.description) {
            isPreviousVisible.set(false)
            isNextVisible.set(false)
            isRefreshVisible.set(true)
            postNumber--
        }
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

    fun refresh() {
        if (postModel.isConnectedToInternet() == true) {
            isPreviousVisible.set(true)
            isNextVisible.set(true)
            isRefreshVisible.set(false)
            getNextPost()
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("gifURL")
        fun loadImage(view: ImageView, url: String?) {
            if (url == NetworkStatus.NO_INTERNET_CONNECTION.description) {
                view.setImageResource(R.drawable.no_internet)
            }
            else if (!url.isNullOrEmpty()) {
                val httpsUrl = "https" + url.slice(4 until url.length)
                Glide
                    .with(view.context)
                    .load(httpsUrl)
                    .error(R.drawable.gif_load_error)
                    .placeholder(R.drawable.loading)
                    .into(view)

            }
        }
    }
}