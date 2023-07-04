package com.example.myapplication.Api

import com.example.myapplication.Model.Data
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface apiInterface {

    @POST("/products")
    fun getData(@Body data : Data) : Call<Data>
}