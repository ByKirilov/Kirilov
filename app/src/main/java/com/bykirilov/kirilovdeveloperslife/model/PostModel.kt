package com.bykirilov.kirilovdeveloperslife.model

import android.os.Handler
import com.bykirilov.kirilovdeveloperslife.network.APIService
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class PostModel {

    private val developerLifeRepository = DeveloperLifeRepository(APIService.create())

    private val posts = mutableListOf<Post>()

    suspend fun getNextPost(index: Int) : Post {
        if (posts.size <= index) {
            posts.add(developerLifeRepository.getRandomPost())
        }

        return posts[index]
    }

    fun getPreviousPost(index: Int) : Post {
        if (index >= 0) {
            return posts[index]
        }
        else {
            throw ArrayIndexOutOfBoundsException()
        }
    }
}