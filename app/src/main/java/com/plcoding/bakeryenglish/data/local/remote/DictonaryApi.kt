package com.plcoding.bakeryenglish.data.local.remote

import com.plcoding.bakeryenglish.data.local.remote.dto.WordInforDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface DictonaryApi {

    @GET("api/v2/entries/en/{word}")
    suspend fun getInforWordDTO(@Path("word") word:String): List<WordInforDTO>

    companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev/"
    }
}