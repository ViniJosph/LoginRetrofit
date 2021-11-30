package com.example.autenticacaorest.service

import com.example.autenticacaorest.model.Cliente
import retrofit2.Call
import retrofit2.http.GET

interface ClienteService {
    @GET("/api/user")
    fun listar(): Call<List<Cliente>>
}