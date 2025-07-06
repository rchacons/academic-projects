package fr.istic.mob.starcn.fragments

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import fr.istic.mob.starcn.MyViewModel
import fr.istic.mob.starcn.R
import fr.istic.mob.starcn.database.AppDatabase
import fr.istic.mob.starcn.database.entity.RoutesEntity
import fr.istic.mob.starcn.database.entity.StopsEntity
import fr.istic.mob.starcn.database.entity.StopsTimeEntity
import fr.istic.mob.starcn.fragments.adapters.StopsTimeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Troisieme fragment qui gere l'affichage des horaires d'une ligne dans un arret
 */
class ThirdFragment : Fragment(), AdapterView.OnItemClickListener {

    lateinit var appContext: Context
    private lateinit var viewModel: MyViewModel
    private lateinit var fragmentListener : FragmentListener

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


    // Variables pour l'affichage des horaires
    lateinit var dateView: TextView
    lateinit var timeView: TextView
    lateinit var stopTimeListView: ListView
    lateinit var stopTimeListAdapter: StopsTimeAdapter
    var stopTimeList: MutableList<StopsTimeEntity> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.fragment_third_land, container, false)
        } else {
            inflater.inflate(R.layout.fragment_third, container, false)
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


        stopTimeListAdapter = StopsTimeAdapter(appContext, stopTimeList)


        CoroutineScope(Dispatchers.IO).launch {
            // On cherche les horaires de l'arret
            var stopsTimes = getStopsTimeFromDatabase(stop, time, date)

            requireActivity().runOnUiThread {
                stopTimeList.clear()

                if(stopsTimes.isNotEmpty())
                    stopTimeList.addAll(stopsTimes)

                else
                    stopTimeList.add(StopsTimeEntity(0,"","Il n'y a pas de departs à la date et à l'heure sélectionnées.","","",""))

                stopTimeListAdapter.notifyDataSetChanged()
            }
        }


    }

    /**
     * Fonction qui permet de recuperer les horaires d'un arret
     */
    private fun getStopsTimeFromDatabase(
        stop: StopsEntity,
        time: String,
        date: Date
    ): List<StopsTimeEntity> {

        var timeList: List<StopsTimeEntity>

        // On parse la date à jour de la semaine pour la recherche en bdd:
        val format1: DateFormat = SimpleDateFormat("EEEE")
        val weekday: String = format1.format(date)
        var monday = 0
        var tuesday = 0
        var wednesday = 0
        var thursday = 0
        var friday = 0
        var saturday = 0
        var sunday = 0

        when (weekday) {
            "Monday" -> monday = 1
            "Tuesday" -> tuesday = 1
            "Wednesday" -> wednesday = 1
            "Thursday" -> thursday = 1
            "Friday" -> friday = 1
            "Saturday" -> saturday = 1
            "Sunday" -> sunday = 1
        }

        // On formate la date au format de la bdd
        val format2: DateFormat = SimpleDateFormat("YYYYMMDD")
        val formattedDay: String = format2.format(date)

        try {
            timeList = appDatabase.stopsTime()
                .getStopTimesWithGivenIdFromTime(
                    stop.id,
                    time,
                    formattedDay,
                    monday,
                    tuesday,
                    wednesday,
                    thursday,
                    friday,
                    saturday,
                    sunday
                )
        } catch (e: Exception) {
            throw e
        }

        return timeList

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("data_key", viewModel.data.value)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appContext = context
        appDatabase = AppDatabase.getDatabase(appContext)
        fragmentListener = context as FragmentListener

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // On affiche la direction
        directionText = view.findViewById(R.id.txt_direction2)
        directionText.text = selectedValuesMap.get("direction") as String

        // On affiche la ligne
        routeText = view.findViewById(R.id.txt_bus)
        routeText.text = route.shortName
        routeText.setBackgroundColor(Color.parseColor("#" + route.color))

        // On affiche l'arret
        stop = selectedValuesMap.get("stop") as StopsEntity
        routeText = view.findViewById(R.id.txt_stop)
        routeText.text = stop.name

        // On affiche le jour
        val format = SimpleDateFormat("dd/MM/yyyy")
        val dateString = format.format(date)

        dateView = view.findViewById(R.id.txt_date)
        dateView.text = dateString


        // On affiche l'heure
        timeView = view.findViewById(R.id.txt_hour)
        timeView.text = time

        // On affiche les horaires
        stopTimeListView = view.findViewById(R.id.list_hours)
        stopTimeListView.onItemClickListener = this

        stopTimeListView.adapter = stopTimeListAdapter


    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedStopTime = parent?.getItemAtPosition(position) as StopsTimeEntity

        // On va sauvergarder les heures de passage

        // On ajoute l'arret selectionnée à notre map et on lance le troisieme fragment
        selectedValuesMap.put("stopTime", selectedStopTime)

        fragmentListener.addFragment(4)

    }



}