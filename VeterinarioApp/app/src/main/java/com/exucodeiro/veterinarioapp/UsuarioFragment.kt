package com.exucodeiro.veterinarioapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.exucodeiro.veterinarioapp.Models.Contato
import com.exucodeiro.veterinarioapp.Models.Endereco
import com.exucodeiro.veterinarioapp.Models.Usuario
import com.exucodeiro.veterinarioapp.Services.UsuarioService
import com.exucodeiro.veterinarioapp.Util.UsuarioPageAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_tab.view.*
import kotlinx.android.synthetic.main.fragment_usuario.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class UsuarioFragment : Fragment() {
    private var usuario = Usuario(1, "Nome", "usuário", "https://cdn2.iconfinder.com/data/icons/medicine-4-1/512/vet-512.png", Endereco(1, "Rua teste 01", 100, "Bloco 2", "Jardim Algo", "1234585", "Ribeirão Preto", "SP", -21.89, -47.23), ArrayList<Contato>())

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_usuario, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        activity.title = getString(R.string.usuario)

        loadData()

        val pageAdapter = UsuarioPageAdapter(childFragmentManager, context, usuario)
        viewPager.adapter = pageAdapter
        tabsUsuario.setupWithViewPager(viewPager)

        val tab = LayoutInflater.from(context).inflate(R.layout.custom_tab, null)
        tab.tabContent.text = "  DETALHES"
        tab.tabContent.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_assignment_black_24dp, 0, 0, 0)

        val tab2 = LayoutInflater.from(context).inflate(R.layout.custom_tab, null)
        tab2.tabContent.text = "  PETS"
        tab2.tabContent.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_pets_black_24dp, 0, 0, 0)

        tabsUsuario.getTabAt(0)?.customView = tab
        //tabsUsuario.getTabAt(0)?.setIcon(R.mipmap.ic_assignment_black_24dp)
        //tabsUsuario.getTabAt(1)?.setIcon(R.mipmap.ic_pets_black_24dp)
        tabsUsuario.getTabAt(1)?.customView = tab2

        imageBack.loadUrl("https://i.ytimg.com/vi/hdxKJsTvvxQ/maxresdefault.jpg")

        super.onActivityCreated(savedInstanceState)
    }

    fun ImageView.loadUrl(url: String) {
        if (!url.equals(""))
            Picasso.with(context).load(url).into(this)
    }

    fun loadData() {
        val usuarioService = UsuarioService()
        async {
            usuario = usuarioService.getUsuario(1)

            uiThread {
                imageIcon.loadUrl(usuario.imagem)
                textNome.text = "${usuario.nome} ${usuario.sobrenome}"
            }
        }
    }

}
