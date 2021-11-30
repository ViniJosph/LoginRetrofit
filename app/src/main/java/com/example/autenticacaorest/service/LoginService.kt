package com.example.autenticacaorest.service

import com.example.autenticacaorest.model.LoginModel
import com.example.autenticacaorest.model.Token
import retrofit2.Call
import retrofit2.http.*

interface LoginService {
    @POST("/api/login")
    fun fazerLogin(@Body login: LoginModel): Call<Token>
}