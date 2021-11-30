package com.example.autenticacaorest.model

data class Cliente (
    var id: Int? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var pix: String? = null,
    var nickname: String? = null,
    var github: String? = null
)