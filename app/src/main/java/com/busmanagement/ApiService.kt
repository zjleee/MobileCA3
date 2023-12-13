package com.busmanagement

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("seats/")
    fun getSeatByNumber(): Call<SeatData>
}