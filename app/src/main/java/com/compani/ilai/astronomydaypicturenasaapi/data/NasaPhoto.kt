package com.compani.ilai.astronomydaypicturenasaapi.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NasaPhoto(
    val copyright: String,
    val date: String,
    val explanation: String,
    val hdurl: String,
    val title: String,
    val url: String
)