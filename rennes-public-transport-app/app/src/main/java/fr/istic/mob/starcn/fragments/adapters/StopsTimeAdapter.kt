package fr.istic.mob.starcn.fragments.adapters

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import fr.istic.mob.starcn.database.entity.StopsTimeEntity

class StopsTimeAdapter(context: Context, stopsTime: List<StopsTimeEntity>) : BaseAdapter()  {

    private var context = context
    private var stopsTime = stopsTime

    override fun getCount(): Int {
        return stopsTime.size
    }

    override fun getItem(arg0: Int): Any {
        return stopsTime[arg0]
    }

    override fun getItemId(arg0: Int): Long {
        return arg0.toLong()
    }

    override fun getView(pos: Int, view: View?, parent: ViewGroup?): View {
        var view: View? = view
        val inflater = LayoutInflater.from(context)
        view = inflater.inflate(R.layout.simple_list_item_1, parent, false)
        val textView = view.findViewById(R.id.text1) as TextView
        textView.text = stopsTime[pos].arrivalTime
        return view

    }

}
