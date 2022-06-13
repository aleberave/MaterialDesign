package ru.geekbrains.materialdesign.viewmodel

import ru.geekbrains.materialdesign.repositoty.PictureOfTheDayResponseData

sealed class PictureOfTheDayAppState {

    data class Success(val pictureOfTheDayResponseData: PictureOfTheDayResponseData) :
        PictureOfTheDayAppState()

    data class Error(val error: Throwable) : PictureOfTheDayAppState()

    data class Loading(val progress: Int?) : PictureOfTheDayAppState()
}