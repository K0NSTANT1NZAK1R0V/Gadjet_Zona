package com.example.test

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class RegisterRequest(
    val email: String,
    val password: String,
    val username: String,
    val phonenumber: String
)
data class LoginRequest(
    val email: String,
    val password: String
)
data class RegisterResponse(val token: String)

data class LoginResponse(val token: String)

interface ApiService {
    @POST("/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>
        @POST("/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}