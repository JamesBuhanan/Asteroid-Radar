package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.network.AsteroidRepository
import com.udacity.asteroidradar.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class AsteroidApiStatus { LOADING, ERROR, DONE }

class MainViewModel(application: Application) : ViewModel() {
    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _status = MutableLiveData<AsteroidApiStatus>()
    val status: LiveData<AsteroidApiStatus>
        get() = _status

    init {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main.immediate) {
                val apiKey = "gHdutngudHFX0kGdm0mEyzSKbT5BhgonZ1X5FpgI"

                try {
                    _status.value = AsteroidApiStatus.LOADING
                    asteroidRepository.saveAsteroids()
                    _pictureOfDay.value = Network.pictureOfDayService.pictureOfDay(apiKey)
                    _status.value = AsteroidApiStatus.DONE
                } catch (ex: Exception) {
                    _status.value = AsteroidApiStatus.ERROR
                }
            }
        }
    }

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)


    val asteroids = asteroidRepository.asteroids

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}