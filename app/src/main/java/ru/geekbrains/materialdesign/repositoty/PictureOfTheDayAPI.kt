package ru.geekbrains.materialdesign.repositoty

import retrofit2.http.GET
import retrofit2.http.Query
import ru.geekbrains.materialdesign.utils.myApiKey
import ru.geekbrains.materialdesign.utils.myDate
import ru.geekbrains.materialdesign.utils.planetaryApod

interface PictureOfTheDayAPI {

    @GET(planetaryApod)
    fun getPictureOfTheDay(@Query(myApiKey) apiKey: String): retrofit2.Call<PictureOfTheDayResponseData>

    @GET(planetaryApod)
    fun getPictureOfTheDayByDate(
        @Query(myApiKey) apiKey: String,
        @Query(myDate) date: String
    ): retrofit2.Call<PictureOfTheDayResponseData>

}