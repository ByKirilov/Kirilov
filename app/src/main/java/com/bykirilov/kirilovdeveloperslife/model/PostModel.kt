package com.bykirilov.kirilovdeveloperslife.model

import android.os.Handler
import com.bykirilov.kirilovdeveloperslife.managers.NetManager
import com.bykirilov.kirilovdeveloperslife.network.APIService
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class PostModel(val netManager: NetManager) {

    private val developerLifeRepository = DeveloperLifeRepository(APIService.create())

    private val posts = mutableListOf<Post>()

    suspend fun getNextPost(index: Int) : Post {
        if (posts.size <= index) {
            netManager.isConnectedToInternet?.let {
                if (it) {
                    posts.add(developerLifeRepository.getRandomPost())
                }
                else {
                    return Post("Нет подключения к интернету", NetworkStatus.NO_INTERNET_CONNECTION.description)
                }
            }
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

    fun isConnectedToInternet() : Boolean? = netManager.isConnectedToInternet
}