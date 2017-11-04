package com.exucodeiro.veterinarioapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exucodeiro.veterinarioapp.Models.Usuario
import kotlinx.android.synthetic.main.fragment_usuario_detail.*


/**
 * A simple [Fragment] subclass.
 */
class UsuarioDetailFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_usuario_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val usuario = arguments.getSerializable("usuario") as Usuario

        textEndereco.text = usuario.endereco?.toString() ?: "Sem endereço registrado"
        textContatos.text = if (usuario.contatos.isNotEmpty()) TextUtils.join(", ", usuario.contatos) else "Sem contatos regitrados"

        super.onActivityCreated(savedInstanceState)
    }

}
