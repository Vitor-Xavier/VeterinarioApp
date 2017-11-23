package com.exucodeiro.veterinarioapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exucodeiro.veterinarioapp.Models.Usuario
import kotlinx.android.synthetic.main.fragment_usuario_detail.*

class UsuarioDetailFragment : Fragment() {
    private var usuario: Usuario? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (arguments != null)
            usuario = arguments.getSerializable("usuario") as Usuario

        return inflater!!.inflate(R.layout.fragment_usuario_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (usuario != null) {
            textEndereco.text = usuario?.endereco?.toString() ?: "Sem endereço registrado"
            textContatos.text = if (usuario?.contatos != null &&
                    usuario?.contatos?.isEmpty() != true) TextUtils.join(", ", usuario?.contatos) else "Sem contatos regitrados"
        }
    }

    companion object {
        private val ARG_USUARIO = "usuario"

        fun newInstance(usuario: Usuario): UsuarioDetailFragment {
            val fragment = UsuarioDetailFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARG_USUARIO, usuario)
            fragment.arguments = bundle
            return fragment
        }
    }
}
