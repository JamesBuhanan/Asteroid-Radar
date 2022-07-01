/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.DatabaseAsteroid


/**
 * Convert Network results to database objects
 */
fun Asteroid.asDatabaseModel() = DatabaseAsteroid(
    id = id,
    codename = codename,
    closeApproachDate = closeApproachDate,
    absoluteMagnitude = absoluteMagnitude,
    estimatedDiameter = estimatedDiameter,
    relativeVelocity = relativeVelocity,
    distanceFromEarth = distanceFromEarth,
    isPotentiallyHazardous = isPotentiallyHazardous,
)

fun DatabaseAsteroid.asDomainModel() = Asteroid(
    id = id,
    codename = codename,
    closeApproachDate = closeApproachDate,
    absoluteMagnitude = absoluteMagnitude,
    estimatedDiameter = estimatedDiameter,
    relativeVelocity = relativeVelocity,
    distanceFromEarth = distanceFromEarth,
    isPotentiallyHazardous = isPotentiallyHazardous,
)

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map { it.asDomainModel() }
}

fun List<Asteroid>.asDatabaseModel(): List<DatabaseAsteroid> {
    return map { it.asDatabaseModel() }
}