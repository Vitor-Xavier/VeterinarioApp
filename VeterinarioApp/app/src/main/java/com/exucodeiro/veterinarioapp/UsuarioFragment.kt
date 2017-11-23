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
import com.exucodeiro.veterinarioapp.Services.LoginSettings
import com.exucodeiro.veterinarioapp.Services.UsuarioService
import com.exucodeiro.veterinarioapp.Util.UsuarioPageAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_tab.view.*
import kotlinx.android.synthetic.main.fragment_usuario.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

class UsuarioFragment : Fragment() {
    private lateinit var usuario: Usuario
    //(1, "Nome", "usuário", "https://cdn2.iconfinder.com/data/icons/medicine-4-1/512/vet-512.png", Endereco(1, "Rua teste 01", 100, "Bloco 2", "Jardim Algo", "1234585", "Ribeirão Preto", "SP", -21.89, -47.23), ArrayList<Contato>())

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_usuario, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.title = getString(R.string.usuario)

        //loadData()
        val usuarioService = UsuarioService()
        val loginSettings = LoginSettings(context)

        async {
            usuario = usuarioService.getUsuario(loginSettings.login.id)

            uiThread {
                imageIcon.loadUrl(usuario.imagem)
                textNome.text = "${usuario.nome} ${usuario.sobrenome}"

                val pageAdapter = UsuarioPageAdapter(childFragmentManager, usuario)
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

                imageBack.loadUrl(R.mipmap.usuario_background)
            }
        }


    }

    fun ImageView.loadUrl(url: String?) {
        if (url != null && url != "")
            Picasso.with(context).load(url).into(this)
    }

    fun ImageView.loadUrl(draw: Int) {
        Picasso.with(context).load(draw).into(this)
    }

    fun loadData() {
        val usuarioService = UsuarioService()
        val loginSettings = LoginSettings(context)
        async {
            usuario = usuarioService.getUsuario(loginSettings.login.id)

            uiThread {
                imageIcon.loadUrl(usuario.imagem)
                textNome.text = "${usuario.nome} ${usuario.sobrenome}"
            }
        }
    }

}
