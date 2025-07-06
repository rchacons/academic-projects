package fr.istic.mob.starcn.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import fr.istic.mob.starcn.database.entity.StopsWithStopTime

class StopsAndStopTimeAdapter(
    context: Context,
    stopWithStopTime: List<StopsWithStopTime>
) : BaseAdapter() {

    private var context = context
    private var stopsWithStopTimeList = stopWithStopTime



    override fun getCount(): Int {
        return stopsWithStopTimeList.size
    }

    override fun getItem(arg0: Int): StopsWithStopTime {
        return stopsWithStopTimeList[arg0]
    }

    override fun getItemId(arg0: Int): Long {
        return arg0.toLong()
    }

    override fun getView(pos: Int, view: View?, parent: ViewGroup?): View {
        var view: View? = view
        val inflater = LayoutInflater.from(context)
        view = inflater.inflate(fr.istic.mob.starcn.R.layout.list_item_stop_stoptime, parent, false)

        val stopView: TextView = view.findViewById(fr.istic.mob.starcn.R.id.list_item_stop)
        val stopTimeView: TextView = view.findViewById(fr.istic.mob.starcn.R.id.list_item_stopTime)

        stopView.text = stopsWithStopTimeList[pos].stopName
        stopTimeView.text = stopsWithStopTimeList[pos].stopTime

        return view

    }

}
