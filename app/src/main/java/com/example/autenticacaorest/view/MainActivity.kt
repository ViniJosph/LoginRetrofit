package com.example.autenticacaorest.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.autenticacaorest.databinding.ActivityMainBinding
import com.example.autenticacaorest.model.LoginModel
import com.example.autenticacaorest.model.Token
import com.example.autenticacaorest.service.API
import com.example.autenticacaorest.service.LOGIN_FILE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            val email = binding.editNomeUsuario.text.toString()
            val password = binding.editSenha.text.toString()
            val device = binding.editSenha.text.toString()

            val login = LoginModel(device, password, email)
            val callback = object: Callback<Token>{
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    val token = response.body()

                    if (response.isSuccessful && token !=null){
                        val editor = getSharedPreferences(LOGIN_FILE, Context.MODE_PRIVATE).edit()
                        editor.putString("email", email)
                        editor.putString("password", password)
                        editor.putString("device_name", device)
                        editor.putString("token", token.token)
                        editor.apply()

                        Toast.makeText(this@MainActivity, "login efetuado", Toast.LENGTH_LONG).show()
                    }
                    else {
                        var msg = response.message().toString()
                        if(msg == ""){
                            msg = "Não foi possivel efetuar login"
                        }
                        Toast.makeText(this@MainActivity, "Não logou", Toast.LENGTH_LONG).show()
                        response.errorBody()?.let {
                            Log.e("MainActivity", it.string())
                        }
                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "On Fail", Toast.LENGTH_LONG).show()
                    Log.e("MainActivity", "onCreate", t)
                }

            }

            API(this).login.fazerLogin(login).enqueue(callback)
        }
    }
}