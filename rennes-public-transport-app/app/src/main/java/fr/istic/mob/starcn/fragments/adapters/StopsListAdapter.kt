package fr.istic.mob.starcn.fragments.adapters

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import fr.istic.mob.starcn.database.entity.StopsEntity

/**
 * Classe adapter qui permet de modifier la valeur de chaque entree de la liste, comme le texte.
 */
class StopsListAdapter(context: Context, stops: List<StopsEntity>) : BaseAdapter() {


    private var context = context
    private var stops = stops

    override fun getCount(): Int {
        return stops.size
    }

    override fun getItem(arg0: Int): Any {
        return stops[arg0]
    }

    override fun getItemId(arg0: Int): Long {
        return arg0.toLong()
    }

    override fun getView(pos: Int, view: View?, parent: ViewGroup?): View {
        var view: View? = view
        val inflater = LayoutInflater.from(context)
        view = inflater.inflate(R.layout.simple_list_item_1, parent, false)
        val textView = view.findViewById(R.id.text1) as TextView
        textView.text = stops[pos].name
        return view

    }

}