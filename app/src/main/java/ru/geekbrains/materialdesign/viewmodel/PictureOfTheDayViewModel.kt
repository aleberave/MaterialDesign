package ru.geekbrains.materialdesign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.materialdesign.BuildConfig
import ru.geekbrains.materialdesign.repositoty.PictureOfTheDayResponseData
import ru.geekbrains.materialdesign.repositoty.PictureOfTheDayRetrofit2Impl

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<PictureOfTheDayAppState> = MutableLiveData(),
    private val pictureOfTheDayRetrofit2Impl: PictureOfTheDayRetrofit2Impl = PictureOfTheDayRetrofit2Impl()
) : ViewModel() {

    fun getLiveData(): LiveData<PictureOfTheDayAppState> {
        return liveData
    }

    fun sendRequest() {
        liveData.postValue(PictureOfTheDayAppState.Loading(null))
        BuildConfig.NASA_API_KEY.let {
            pictureOfTheDayRetrofit2Impl.getRetrofit().getPictureOfTheDay(it)
                .enqueue(callback)
        }
    }

    fun sendRequest(date: String) {
        liveData.postValue(PictureOfTheDayAppState.Loading(null))
        BuildConfig.NASA_API_KEY.let {
            pictureOfTheDayRetrofit2Impl.getRetrofit()
                .getPictureOfTheDayByDate(it, date)
                .enqueue(callback)
        }
    }

    private val callback = object : Callback<PictureOfTheDayResponseData> {
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(PictureOfTheDayAppState.Success(it))
                }
            } else {
                onFailure(call, response.errorBody() as Throwable)
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            TODO("Not yet implemented")
        }

    }
}