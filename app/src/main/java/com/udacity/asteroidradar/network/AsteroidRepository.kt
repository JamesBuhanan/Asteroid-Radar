package com.udacity.asteroidradar.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidsDatabase) {
    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun saveAsteroids() {
        withContext(Dispatchers.IO) {
            val jsonString = Network.asteroidService
                .getAsteroids("gHdutngudHFX0kGdm0mEyzSKbT5BhgonZ1X5FpgI")
            val jsonObject = JSONObject(jsonString)
            val asteroids = parseAsteroidsJsonResult(jsonObject)
            val databaseAsteroids = asteroids.asDatabaseModel().toTypedArray()
            database.clearAllTables()
            database.asteroidDao.insertAll(*databaseAsteroids)
        }
    }
}