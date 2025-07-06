package fr.istic.mob.starcn.fragments.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import fr.istic.mob.starcn.database.entity.RoutesWithDirection

class RouteAndDirectionAdapter(
    context: Context,
    routeWithDirection: List<RoutesWithDirection>
) : BaseAdapter()
{

    private var context = context
    private var stopsWithDirectionList = routeWithDirection


    override fun getCount(): Int {
        return stopsWithDirectionList.size
    }

    override fun getItem(arg0: Int): RoutesWithDirection {
        return stopsWithDirectionList[arg0]
    }

    override fun getItemId(arg0: Int): Long {
        return arg0.toLong()
    }

    override fun getView(pos: Int, view: View?, parent: ViewGroup?): View {
        var view: View? = view
        val inflater = LayoutInflater.from(context)
        view = inflater.inflate(fr.istic.mob.starcn.R.layout.list_item_route_direction, parent, false)

        val stopView: TextView = view.findViewById(fr.istic.mob.starcn.R.id.list_item_route)
        val directionView: TextView = view.findViewById(fr.istic.mob.starcn.R.id.list_item_direction)

        stopView.text = stopsWithDirectionList[pos].route.shortName
        if(stopsWithDirectionList[pos].route.color != "")
            stopView.setBackgroundColor(Color.parseColor("#" + stopsWithDirectionList[pos].route.color))

        directionView.text = stopsWithDirectionList[pos].direction

        return view

    }
}