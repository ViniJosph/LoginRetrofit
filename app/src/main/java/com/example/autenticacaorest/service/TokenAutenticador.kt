package com.example.autenticacaorest.service

import android.content.Context
import com.example.autenticacaorest.model.LoginModel
import okhttp3.*

const val LOGIN_FILE = "login"

class TokenAutenticador(private val context: Context):Interceptor, Authenticator {
    override fun intercept(chain: Interceptor.Chain): Response {
        val prefs = context.getSharedPreferences(LOGIN_FILE ,Context.MODE_PRIVATE)

        val token = prefs.getString("token", "") as String

        var request = chain.request()

        request = request?.newBuilder()?.addHeader("token", token)?.build()

        return chain.proceed(request)
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val prefs = context.getSharedPreferences(LOGIN_FILE, Context.MODE_PRIVATE)

        val login = LoginModel("device_name", "password", "email")

        val respostaRetrofit = API(context).login.fazerLogin(login).execute()

        var token = respostaRetrofit.body()
        if (respostaRetrofit.isSuccessful && token != null) {
            val editor = prefs.edit()
            editor.putString("token", token.token)
            editor.apply()

            return response?.request()?.newBuilder()?.header("token", token.token)?.build()
        }

        return null
    }

}