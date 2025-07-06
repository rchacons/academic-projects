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
import androidx.lifecycle.ViewModelProvider
import fr.istic.mob.starcn.MyViewModel
import fr.istic.mob.starcn.R
import fr.istic.mob.starcn.database.AppDatabase
import fr.istic.mob.starcn.database.entity.RoutesEntity
import fr.istic.mob.starcn.database.entity.StopsEntity
import fr.istic.mob.starcn.fragments.adapters.StopsListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


/**
 * Deuxieme fragment qui gere l'affichage de la direction avec les possibles arrets
 */
class SecondFragment : Fragment(),
    AdapterView.OnItemClickListener {

    lateinit var appContext: Context
    private lateinit var viewModel: MyViewModel
    private lateinit var fragmentListener : FragmentListener



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

    // Variables pour l'affichage des arrets
    lateinit var stopListView: ListView
    lateinit var stopListAdapter: StopsListAdapter
    var stopList: MutableList<StopsEntity> = mutableListOf()

    // Base de donees
    private lateinit var appDatabase: AppDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.fragment_second_land, container, false)
        } else {
            inflater.inflate(R.layout.fragment_second, container, false)
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
        directionText = view.findViewById(R.id.txt_direction)
        directionText.text = selectedValuesMap.get("direction") as String

        // On affiche la ligne
        routeText = view.findViewById(R.id.txt_ligne)
        routeText.text = route.shortName
        routeText.setBackgroundColor(Color.parseColor("#" + route.color))

        // On affiche les arrets
        stopListView = view.findViewById(R.id.list_stops)
        stopListView.onItemClickListener = this

        stopListAdapter = StopsListAdapter(appContext, stopList)
        stopListView.adapter = stopListAdapter

        CoroutineScope(Dispatchers.IO).launch {
            // On cherche les arrets avec la ligne et la direction
            var stops = getStopsFromDatabase(route, direction)


            requireActivity().runOnUiThread {
                stopList.clear()

                stopList.addAll(stops)
                stopListAdapter.notifyDataSetChanged()
            }


        }
    }

    /**
     * Fonction qui permet de recuperer les arrets a partir d'une ligne et sa direction.
     */
    fun getStopsFromDatabase(route: RoutesEntity, direction: String): List<StopsEntity> {
        return appDatabase.stops().searchStopWithRouteAndDirection(route.id, direction)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            val selectedStop = parent?.getItemAtPosition(position) as StopsEntity

            // On va sauvegarder les arrets entre l'arret choisie et la terminal pour les utiliser apres
            var slicedList: List<StopsEntity> = stopList.toList()
            slicedList = slicedList.drop(position + 1)


            // On ajoute l'arret selectionnée à notre map et on lance le troisieme fragment
            selectedValuesMap.put("stop", selectedStop)
            selectedValuesMap.put("stopList", slicedList)

            fragmentListener.addFragment(3)



        }
    }


}