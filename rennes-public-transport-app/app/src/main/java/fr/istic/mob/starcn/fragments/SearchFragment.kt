package fr.istic.mob.starcn.fragments

import android.app.SearchManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.HashMap

/**
 * Fragment qui permet la recherche textuelle des arrets
 */
class SearchFragment : Fragment(), AdapterView.OnItemClickListener {



    lateinit var appContext: Context
    private lateinit var viewModel: MyViewModel
    private lateinit var fragmentListener : FragmentListener

    // Base de donees
    private lateinit var appDatabase: AppDatabase

    lateinit var stopListView: ListView
    lateinit var stopListAdapter: StopsListAdapter
    var stopList: MutableList<StopsEntity> = mutableListOf()

    private var selectedValuesMap = hashMapOf<String, Any>()


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        // on recupere la data dans savedInstanceState
        if (savedInstanceState != null) {
            val data =
                savedInstanceState.getSerializable("data_key") as java.util.HashMap<String, Any>
            data?.let { viewModel.setData(it) }
            selectedValuesMap = data
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view : View? = null

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view =  inflater.inflate(R.layout.fragment_search_land,container,false)
        }
        else{
            view =  inflater.inflate(R.layout.fragment_search,container,false)
        }


        val searchView = view.findViewById<SearchView>(R.id.srchFragment)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // On recherche l'arret saisie
                searchQuery(query)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Pour chaque lettre, on recherche l'arret qui se ressemble
                searchQuery(newText)
                return true
            }
        })

        // La liste dans laquelle on affichera les resultats de la recherche
        stopListView = view.findViewById(R.id.search_list_view)
        stopListView.onItemClickListener = this

        stopListAdapter = StopsListAdapter(requireContext(), stopList)
        stopListView.adapter = stopListAdapter


        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("data_key", viewModel.data.value)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appContext = context
        // On demarre notre base de donnees
        appDatabase = AppDatabase.getDatabase(context)
        fragmentListener = context as FragmentListener
    }

    /**
     * Recherche en base l'arret saisie et met a jour la liste
     */
    private fun searchQuery(query: String?) {

        var job : Job? = null
        if(viewModel.data.value?.get("downloadJob") != null){
            job = viewModel.data.value?.get("downloadJob") as Job

            if(job.isCompleted){
                if(query != null && query != ""){
                    // search data
                    CoroutineScope(Dispatchers.IO).launch {
                        searchStopInDatabase(query)
                    }
                }
                else{
                    stopList.clear()
                    stopListAdapter.notifyDataSetChanged()
                }
            }
            else{
                var message = "Veuillez attendre le remplissage de la base pour effectuer une recherche"
                Toast.makeText(appContext, message, Toast.LENGTH_LONG).show()
            }
        }



    }

    private fun searchStopInDatabase(query: String) {
        var stops = appDatabase.stops().getStopsFromGivenText(query)

        stopList.clear()

        if(stops != null){
            stopList.addAll(stops)

            // On notifie le spinner depuis le main thread
            requireActivity().runOnUiThread {
                stopListAdapter.notifyDataSetChanged()
            }
        }


    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val stop = parent?.getItemAtPosition(position) as StopsEntity


        if(viewModel.data.value != null){
            viewModel.data.value!!.put("stopSearch", stop)
        }

        fragmentListener.addFragment(5)
    }

}