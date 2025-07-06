package fr.istic.mob.starcn.fragments

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.istic.mob.starcn.MyViewModel
import fr.istic.mob.starcn.R
import fr.istic.mob.starcn.database.AppDatabase
import fr.istic.mob.starcn.database.entity.RoutesEntity
import fr.istic.mob.starcn.database.entity.StopsEntity
import fr.istic.mob.starcn.database.entity.StopsTimeEntity
import fr.istic.mob.starcn.database.entity.StopsWithStopTime
import fr.istic.mob.starcn.fragments.adapters.StopsAndStopTimeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * Quatrieme fragment qui gere l'afﬁchage des heures de passage du bus choisi entre l’arrêt choisi et le
terminus du trajet
 */
class FourthFragment : Fragment(), AdapterView.OnItemClickListener {

    lateinit var appContext: Context
    private lateinit var viewModel: MyViewModel


    // Base de donees
    private lateinit var appDatabase: AppDatabase

    // Variables pour l'affichage de la ligne
    private lateinit var routeText: TextView

    // Variables pour l'affichage de la direction
    private var selectedValuesMap = hashMapOf<String, Any>()
    private lateinit var directionText: TextView

    // Variables transmises par le premier fragment
    lateinit var route: RoutesEntity
    lateinit var date: Date
    lateinit var time: String
    lateinit var direction: String
    lateinit var stop: StopsEntity
    lateinit var stopTime: StopsTimeEntity
    lateinit var stopLists: List<StopsEntity>

    // Variables pour l'affichage des horaires
    lateinit var stopTimeText: TextView
    lateinit var stopsWithstopTimeListView: ListView
    lateinit var stopsWithStopTimeListAdapter: StopsAndStopTimeAdapter
    var stopsWithStopTimeList: MutableList<StopsWithStopTime> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.fragment_fourth_land, container, false)
        } else {
            inflater.inflate(R.layout.fragment_fourth, container, false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        // on recupere la data dans savedInstanceState
        if (savedInstanceState != null) {
            val data =
                savedInstanceState.getSerializable("data_key") as java.util.HashMap<String, Any>
            data?.let { viewModel.setData(it) }
            selectedValuesMap = data
        } else {
            // On recupere la data dans viewModel
            selectedValuesMap = viewModel.data.value!!
        }

        route = selectedValuesMap.get("bus") as RoutesEntity
        date = selectedValuesMap.get("date") as Date
        time = selectedValuesMap.get("time") as String
        direction = selectedValuesMap.get("direction") as String
        stop = selectedValuesMap.get("stop") as StopsEntity
        stopTime = selectedValuesMap.get("stopTime") as StopsTimeEntity
        stopLists = selectedValuesMap.get("stopList") as List<StopsEntity>



        CoroutineScope(Dispatchers.IO).launch {

            var stopsWithSTList = mutableListOf<StopsWithStopTime>()

            // On cherche les horaires de chaque arret
            for (stop in stopLists) {
                val stopPassageTime =
                    appDatabase.stopsTime().getStopTimeFromTripAndStop(stopTime.tripId, stop.id)

                if (stopPassageTime != null) {
                    // On l'enregistre dans la data classe pour l'afficher plus tard
                    stopsWithSTList.add(
                        StopsWithStopTime(
                            stop.name,
                            stopPassageTime.arrivalTime
                        )
                    )
                } else {
                    stopsWithSTList.add(
                        StopsWithStopTime(
                            stop.name,
                            "Pas d'heure de passage pour cet arret"
                        )
                    )
                }

            }

            if (this@FourthFragment.isAdded) {
                // On met a jour la view
                requireActivity().runOnUiThread {
                    stopsWithStopTimeList.clear()

                    stopsWithStopTimeList.addAll(stopsWithSTList)
                    stopsWithStopTimeListAdapter.notifyDataSetChanged()
                }
            }


        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("data_key", viewModel.data.value)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        appContext = context
        appDatabase = AppDatabase.getDatabase(appContext)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // On affiche la direction
        directionText = view.findViewById(R.id.txt_direction3)
        directionText.text = selectedValuesMap.get("direction") as String

        // On affiche la ligne
        routeText = view.findViewById(R.id.txt_bus2)
        routeText.text = route.shortName
        routeText.setBackgroundColor(Color.parseColor("#" + route.color))

        // On affiche l'arret
        routeText = view.findViewById(R.id.txt_stop2)
        routeText.text = stop.name

        // On affiche l'heure de passage choisie
        stopTimeText = view.findViewById(R.id.txt_stopTime)
        stopTimeText.text = stopTime.arrivalTime

        // On affiche les horaires
        stopsWithstopTimeListView = view.findViewById(R.id.list_hoursStops)
        stopsWithstopTimeListView.onItemClickListener = this

        stopsWithStopTimeListAdapter = StopsAndStopTimeAdapter(appContext, stopsWithStopTimeList)
        stopsWithstopTimeListView.adapter = stopsWithStopTimeListAdapter


    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedStopTime = parent?.getItemAtPosition(position) as StopsWithStopTime
        Log.i("INFO", "Clicked on stop time:" + selectedStopTime)


    }


}