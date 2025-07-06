package fr.istic.mob.starcn.fragments

import android.content.Context
import android.content.res.Configuration
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
import fr.istic.mob.starcn.database.entity.RoutesWithDirection
import fr.istic.mob.starcn.database.entity.StopsEntity
import fr.istic.mob.starcn.fragments.adapters.RouteAndDirectionAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FifthFragment : Fragment(), AdapterView.OnItemClickListener {

    lateinit var appContext: Context
    private lateinit var viewModel: MyViewModel

    // Base de donees
    private lateinit var appDatabase: AppDatabase

    // Variables pour l'affichage des lignes avec ses directions
    lateinit var stopText: TextView
    lateinit var routesWithDirectionListView: ListView
    lateinit var routesWithDirectionListAdapter: RouteAndDirectionAdapter
    var routesWithDirectionList: MutableList<RoutesWithDirection> = mutableListOf()

    private var selectedValuesMap = hashMapOf<String, Any>()

    // Variables transmises par le search fragment
    lateinit var stop: StopsEntity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        appContext = context
        appDatabase = AppDatabase.getDatabase(appContext)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]
        //viewModel.data.value?.put("number",5)

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

        stop = selectedValuesMap.get("stopSearch") as StopsEntity

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("data_key", viewModel.data.value)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.fragment_fifth_land, container, false)
        } else {
            inflater.inflate(R.layout.fragment_fifth, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // On affiche l'arret choisie
        stopText = view.findViewById(R.id.txt_stop3)
        stopText.text = stop.name

        //On affiche les bus avec ses directions
        routesWithDirectionListView = view.findViewById(R.id.list_busDirection)
        routesWithDirectionListView.onItemClickListener = this

        routesWithDirectionListAdapter =
            RouteAndDirectionAdapter(appContext, routesWithDirectionList)
        routesWithDirectionListView.adapter = routesWithDirectionListAdapter

        CoroutineScope(Dispatchers.IO).launch {

            var routesWithDirection = mutableListOf<RoutesWithDirection>()

            // On cherche les lignes passant pour chaque arret
            var routes = getRoutesOnStopFromDatabase(stop)

            for (route in routes) {
                var directions = getDirectionsFromRoute(route)

                if(directions.size == 2){
                    // Cas exceptionnel (par ex si on fait group by sur direction_id sur la ligne C7 où ca renvoie la meme direction)
                    if(directions[0] == directions[1]){
                        directions = appDatabase.trips().getAllTripDirectionFromRoute(route.id)
                    }
                }

                for (direction in directions) {
                    // On ajoute les routes -> directions a la liste
                    routesWithDirection.add(RoutesWithDirection(route, direction))
                }
            }



            requireActivity().runOnUiThread {
                routesWithDirectionList.clear()

                routesWithDirectionList.addAll(routesWithDirection)
                routesWithDirectionListAdapter.notifyDataSetChanged()
            }
        }
    }

    /**
     * Cherche en base tous les lignes passant par l'arret donné
     */
    private fun getRoutesOnStopFromDatabase(stop: StopsEntity): List<RoutesEntity> {
        return appDatabase.routes().getRoutesFromStopId(stop.name)
    }

    /**
     * Cherche en base tous les directions de la ligne donne
     */
    private fun getDirectionsFromRoute(route: RoutesEntity): List<String> {
        return appDatabase.trips().getTripDirectionFromRoute(route.id)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedRoutesWithDirection = parent?.getItemAtPosition(position) as RoutesWithDirection
        Log.i(
            "INFO",
            "Clicked on route: ${selectedRoutesWithDirection.route.shortName} , direction: ${selectedRoutesWithDirection.direction} "
        )
    }
}