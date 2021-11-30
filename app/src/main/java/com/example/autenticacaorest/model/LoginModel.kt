package com.example.autenticacaorest.model

data class LoginModel(
	val device_name: String? = null,
	val password: String? = null,
	val email: String? = null
)
