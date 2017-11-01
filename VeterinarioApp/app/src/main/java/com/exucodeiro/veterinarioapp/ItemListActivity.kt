package com.exucodeiro.veterinarioapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.exucodeiro.veterinarioapp.Models.Item
import com.exucodeiro.veterinarioapp.Models.Sobre
import com.exucodeiro.veterinarioapp.Util.ItemAdapter

import com.exucodeiro.veterinarioapp.dummy.DummyContent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import org.jetbrains.anko.toast

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {
    private var adapter: ItemAdapter? = null
    private var itens: ArrayList<Item> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        imageAvatar.loadUrl("https://cdn2.iconfinder.com/data/icons/medicine-4-1/512/vet-512.png")

        itens.add(Item(1, R.mipmap.ic_home_black_24dp, "Início", "Profissionais da área animal"))
        itens.add(Item(2, R.mipmap.ic_person_black_24dp, "Perfil", "Página pessoal do usuário"))
        itens.add(Item(3, R.mipmap.ic_event_note_black_24dp, "Agenda", "Consultas agendadas para seus pets"))
        itens.add(Item(4, R.mipmap.ic_info_black_24dp, "Sobre", "Informações técnicas sobre o app"))

        adapter = ItemAdapter(itens, this)
        listMaster.adapter = adapter

        listMaster.setOnItemClickListener { parent, view, position, id ->
            val item = ((adapter as ItemAdapter).getItem(position) as Item)

            val it: Intent
            when(item.itemId) {
                1 ->
                {
                    it = Intent(this, MainActivity::class.java)
                    it.putExtra("activity", "master")
                }
                2 -> it = Intent(this, UsuarioDetailActivity::class.java)
                3 -> it = Intent(this, ConsultaActivity::class.java)
                4 -> it = Intent(this, SobreActivity::class.java)
                else -> it = Intent(this, MainActivity::class.java)
            }
            startActivity(it)
        }
    }

    fun ImageView.loadUrl(url: String) {
        if (url != null && !url.equals(""))
            Picasso.with(context).load(url).into(this)
    }

}
