package com.valorizeseusamigos.fala_comigo.autenticacao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.valorizeseusamigos.fala_comigo.MainActivity
import com.valorizeseusamigos.fala_comigo.R
import com.valorizeseusamigos.fala_comigo.databinding.ActivityCadastroBinding
import com.valorizeseusamigos.fala_comigo.databinding.ActivityLoginBinding
import com.valorizeseusamigos.fala_comigo.utils.exibirMensagem

class CadastroActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }

    private lateinit var nome: String
    private lateinit var email: String
    private lateinit var senha: String

    //Firebase
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializarToolbar()
        configClicks()

    }

    private fun configClicks() {
       binding.btnCadastrar.setOnClickListener {
           if(validarCampos()){

              cadastrarUsuario(nome, email, senha)

           }
       }
    }

    private fun cadastrarUsuario(nome: String, email: String, senha: String) {

        firebaseAuth.createUserWithEmailAndPassword(
            email, senha
        ).addOnCompleteListener { resultado ->
            if(resultado.isSuccessful){

                exibirMensagem("Cadastro realizado com sucesso!")
                //Toast.makeText(applicationContext, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        }.addOnFailureListener { erro ->
            try {
               throw erro
            }catch (erroSenhaFraca: FirebaseAuthWeakPasswordException){
                erroSenhaFraca.printStackTrace()
                exibirMensagem("Senha fraca. Digite outra senha com letras, números e caractéres especiais")
            }catch (erroUsuarioExistente: FirebaseAuthUserCollisionException){
                erroUsuarioExistente.printStackTrace()
                exibirMensagem("Este E-mail já está cadastrado")
            }catch (erroCredenciaisInvalidas: FirebaseAuthInvalidCredentialsException){
                erroCredenciaisInvalidas.printStackTrace()
                exibirMensagem("E-mail inválido")
            }
        }
    }

    private fun validarCampos(): Boolean {

        nome = binding.editNome.text.toString()
        email = binding.editEmail.text.toString()
        senha = binding.editSenha.text.toString()

        if( nome.isNotEmpty() ){

            binding.textInputNome.error = null
            if( email.isNotEmpty() ){

                binding.textInputEmail.error = null
                if( senha.isNotEmpty() ){
                    binding.textInputSenha.error = null
                    return true
                }else{
                    binding.textInputSenha.error = "Preencha a senha"
                    return false
                }

            }else{
                binding.textInputEmail.error = "Preencha o seu e-mail!"
                return false
            }

        }else{
            binding.textInputNome.error = "Preencha o seu nome!"
            return false
        }



    }

    private fun inicializarToolbar(){
        val toolbar = binding.includeToolbar.tbPrincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Faça o seu cadastro"
            setDisplayHomeAsUpEnabled(true)
        }
    }

}