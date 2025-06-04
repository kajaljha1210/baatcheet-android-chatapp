package com.example.baatcheet.data.apiservices
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TwilioApiService {
    @FormUrlEncoded
    @POST("Messages.json")
    suspend fun sendSms(
        @Field("From") from: String,
        @Field("To") to: String,
        @Field("Body") body: String
    ): ResponseBody
}