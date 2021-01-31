package com.bykirilov.kirilovdeveloperslife.model

import com.bykirilov.kirilovdeveloperslife.network.APIService

class DeveloperLifeRepository(val apiService: APIService) {

    suspend fun getRandomPost(): Post {
        return apiService.getRandomPost()
    }
}