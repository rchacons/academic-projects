package fr.istic.mob.starcn.fragments.adapters

import android.R
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import fr.istic.mob.starcn.database.entity.RoutesEntity


/**
 * Classe adapter qui permet de modifier les valeurs de chaque entree du spinner, comme la couleur
 * et son texte.
 */
internal class SpinnerAdapter(context: Context, routes: List<RoutesEntity>) : BaseAdapter() {

    private var context = context
    private var routes = routes

    override fun getCount(): Int {
        return routes.size
    }

    override fun getItem(arg0: Int): Any {
        return routes[arg0]
    }

    override fun getItemId(arg0: Int): Long {
        return arg0.toLong()
    }

    override fun getView(pos: Int, view: View?, parent: ViewGroup?): View {
        var view: View? = view
        val inflater = LayoutInflater.from(context)
        view = inflater.inflate(R.layout.simple_list_item_1, parent,false)
        val textView = view.findViewById(R.id.text1) as TextView
        if(routes[pos].color != "")
            textView.setBackgroundColor(Color.parseColor("#"+routes[pos].color))
        textView.textSize = 15f
        textView.text = routes[pos].shortName

        return view
    }
}