package com.compani.ilai.astronomydaypicturenasaapi.api

import com.compani.ilai.astronomydaypicturenasaapi.data.NasaPhoto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface NasaApi {

    @GET("planetary/apod")
    fun getPhoto(): Single<NasaPhoto>
}