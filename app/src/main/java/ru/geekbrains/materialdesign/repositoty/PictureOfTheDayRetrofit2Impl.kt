package ru.geekbrains.materialdesign.repositoty

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrains.materialdesign.utils.nasaBaseUrl

class PictureOfTheDayRetrofit2Impl {

    fun getRetrofit(): PictureOfTheDayAPI {
        val pictureOfTheDayRetrofit = Retrofit.Builder()
            .baseUrl(nasaBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return pictureOfTheDayRetrofit.create(PictureOfTheDayAPI::class.java)
    }

}