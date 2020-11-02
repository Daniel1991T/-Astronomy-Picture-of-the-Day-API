package com.compani.ilai.astronomydaypicturenasaapi.data

import com.compani.ilai.astronomydaypicturenasaapi.api.NasaApi
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NasaPhotoRepository @Inject constructor(
    private val nasaApi: NasaApi
) {

    fun getNasaPhoto(): Single<NasaPhoto> {
        return nasaApi.getPhoto()
            .subscribeOn(Schedulers.io())
    }
}