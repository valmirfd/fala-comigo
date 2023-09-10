package com.valorizeseusamigos.fala_comigo.autenticacao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.valorizeseusamigos.fala_comigo.R
import com.valorizeseusamigos.fala_comigo.databinding.ActivityLoginBinding
import com.valorizeseusamigos.fala_comigo.databinding.ActivityRecuperaContaBinding

class RecuperaContaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRecuperaContaBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configClicks()
    }

    private fun configClicks(){

        binding.textLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
}