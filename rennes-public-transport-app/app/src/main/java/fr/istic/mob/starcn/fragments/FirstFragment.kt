package fr.istic.mob.starcn.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.istic.mob.starcn.FileDownloader
import fr.istic.mob.starcn.MyViewModel
import fr.istic.mob.starcn.R
import fr.istic.mob.starcn.database.AppDatabase
import fr.istic.mob.starcn.database.entity.RoutesEntity
import fr.istic.mob.starcn.fragments.adapters.SpinnerAdapter
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*



class FirstFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var appContext: Context
    private lateinit var viewModel: MyViewModel
    private lateinit var fragmentListener : FragmentListener

    // Buttons
    private lateinit var btnDatePicker: Button
    private lateinit var btnTimePicker: Button
    private lateinit var btnSearch: Button

    // Variables pour choisir le temp
    var txtDate: EditText? = null
    var txtTime: EditText? = null

    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0
    private var mHour = 0
    private var mMinute = 0

    // Variables du service de telechargement
    private lateinit var downloadJob: Job
    private lateinit var notificationJob: Job
    lateinit var fileDownloader: FileDownloader
    private var link =
      "https://data.explore.star.fr/explore/dataset/tco-busmetro-horaires-gtfs-versions-td/download/?format=csv&timezone=Europe/Berlin&lang=fr&use_labels_for_header=true&csv_separator=%3B"


    private lateinit var spinnerBus: Spinner
    private lateinit var spinnerBusAdapter: SpinnerAdapter

    private lateinit var spinnerDirection: Spinner
    private lateinit var spinnerDirectionAdapter: ArrayAdapter<String>

    private var busList: MutableList<RoutesEntity> = mutableListOf()
    private var directionList: MutableList<String> = mutableListOf()

    // Bar de progres
    private lateinit var progressBar: ProgressBar
    private lateinit var progressBarText: TextView
    private lateinit var newBaseText: TextView

    // Base de donees
    private lateinit var appDatabase: AppDatabase
    private var selectedValuesMap = hashMapOf<String, Any>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        // on recupere le data dans savedInstanceState
        if (savedInstanceState != null) {
            val data = savedInstanceState.getSerializable("data_key") as HashMap<String, Any>
            data?.let { viewModel.setData(it) }
            selectedValuesMap = data
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.fragment_first_land, container, false)
        } else {
            inflater.inflate(R.layout.fragment_first, container, false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("data_key", viewModel.data.value)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appContext = context
        fragmentListener = context as FragmentListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // On initialise la barre de progression
        progressBar = view.findViewById(R.id.progress_bar)
        progressBarText = view.findViewById(R.id.txt_progress_bar)
        newBaseText = view.findViewById(R.id.txt_new_base)



        // On demarre notre base de donnees
        appDatabase = AppDatabase.getDatabase(appContext)

        // On initialise les buttons
        txtDate = view.findViewById(R.id.in_date)
        txtTime = view.findViewById(R.id.in_time)
        createDatePickerButton()
        createTimePickerButton()
        createSearchButton()


        // On initialise les spinners
        spinnerBus = view.findViewById(R.id.spinner_bus)
        spinnerBus.onItemSelectedListener = this

        spinnerBusAdapter = SpinnerAdapter(appContext, busList)
        spinnerBus.adapter = spinnerBusAdapter

        spinnerDirection = view.findViewById(R.id.spinner_direction)
        spinnerDirection.onItemSelectedListener = this

        spinnerDirectionAdapter =
            ArrayAdapter(appContext, android.R.layout.simple_list_item_1, directionList)
        spinnerDirection.adapter = spinnerDirectionAdapter


        // On verifie s'il y a des donnees existants (dans le cas d'une rotation ou d'une notification appuye)
        if (viewModel.data.value != null) {

            selectedValuesMap = viewModel.data.value!!
            var newBase = viewModel.data.value?.get("baseValue")
            var baseUpdated = viewModel.data.value?.get("baseUpdated")

            if(newBase != null && baseUpdated == null ){
                fileDownloader = FileDownloader(appContext, viewModel)

                fileDownloader.progressBarText = progressBarText
                newBase = newBase as AbstractMap.SimpleEntry<String, String>
                // On telecharge la nouvelle base
                try{
                    downloadJob = CoroutineScope(Dispatchers.Main).launch {
                        // On efface la selection des bus et direction

                        updateSelectedData(selectedValuesMap)


                        progressBar.visibility = View.VISIBLE
                        progressBarText.visibility = View.VISIBLE
                        newBaseText.visibility = View.VISIBLE

                        // On efface le contenu des spinners
                        busList.clear()
                        directionList.clear()

                        spinnerBusAdapter.notifyDataSetChanged()
                        spinnerDirectionAdapter.notifyDataSetChanged()

                        withContext(Dispatchers.IO) {

                            // On lance le service de telechargement
                            fileDownloader.downloadZipFileFromBaseUrl(newBase)


                            // Une fois fini, on alimente le spinner
                            populateBusSpinner()
                            viewModel.data.value!!.put("baseUpdated",true)
                        }

                        progressBarText.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        newBaseText.visibility = View.GONE
                    }

                    viewModel.data.value!!.put("downloadJob",downloadJob)

                }
                catch (e: Exception) {
                    // Si on a pas pu remplir la nouvelle base, on relance la notification
                    startRepeatingNotificationJob(15000,downloadJob)
                    Log.e("ERROR", "Erreur lors du remplissage de la nouvelle base $e")
                }

            }
            // C'est juste une rotation
            else{

                selectedValuesMap = viewModel.data.value!!
                var job : Job? = null
                if(selectedValuesMap.get("downloadJob") != null){
                    job = selectedValuesMap.get("downloadJob") as Job
                }

                downloadJob = CoroutineScope(Dispatchers.IO).launch {
                    if (job != null) {
                        withContext(Dispatchers.Main) {
                            progressBarText.visibility = View.VISIBLE
                            progressBar.visibility = View.VISIBLE
                        }
                        job.join()


                    }
                    populateBusSpinner()
                    updateSelectedData(selectedValuesMap)
                }

            }

        }
        // Premier demarrage du fragment sans donnees.
        else {

            viewModel.data.value = selectedValuesMap
            fileDownloader = FileDownloader(appContext, viewModel)
            fileDownloader.progressBarText = progressBarText

            // Sinon, on lance le service de telechargement et remplissage de la bdd
            downloadJob = CoroutineScope(Dispatchers.Main).launch {

                progressBar.visibility = View.VISIBLE

                withContext(Dispatchers.IO) {
                    // On lance le service de telechargement
                    fileDownloader.asyncDownloadFileFromWeb(link)

                    // Une fois fini, on alimente le spinner
                    populateBusSpinner()
                }




                // Une fois le remplissage de la bdd fini, on lance le service qui verifie s'il y a un nouveau fichier
                startRepeatingNotificationJob(15000L,downloadJob)

            }

            viewModel.data.value!!.put("downloadJob",downloadJob)


        }


    }

    /**
     * Met à jour les text view et spinners en fonction de ce qu'il y a dans le map.
     * Cette fonction est declenchée dans le cas d'une reconstruction du fragment (rotation)
     */
    private suspend fun updateSelectedData(selectedValuesMap: HashMap<String, Any>) {

        val route = selectedValuesMap.get("bus") as RoutesEntity?
        val routeSelection = selectedValuesMap.get("busSelection") as Int?
        val date = selectedValuesMap.get("inputDate") as String?
        val time = selectedValuesMap.get("time") as String?
        val direction = selectedValuesMap.get("direction") as String?
        val directionSelection = selectedValuesMap.get("directionSelection") as Int?

        withContext(Dispatchers.Main) {
            if (date != null) {
                txtDate!!.setText(date)
            }
            if (time != null) {
                txtTime!!.setText(time)
            }
            if (routeSelection != null) {
                spinnerBus.setSelection(routeSelection)
            }
            if (directionSelection != null) {
                spinnerDirection.setSelection(directionSelection)
            }

            progressBarText.visibility = View.GONE
            progressBar.visibility = View.GONE
        }


    }


    private fun createDatePickerButton() {

        btnDatePicker = requireView().findViewById(R.id.btn_date)

        btnDatePicker.setOnClickListener {
            var calendar: Calendar = Calendar.getInstance()
            // Get Current Date
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);


            val datePickerDialog = DatePickerDialog(
                appContext, { _, year, monthOfYear, dayOfMonth ->

                    val inputDate = "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year
                    val format = SimpleDateFormat("dd/MM/yyyy")
                    val date: Date = format.parse(inputDate)

                    txtDate?.setText(inputDate)


                    viewModel.data.value?.put("date", date)
                    viewModel.data.value?.put("inputDate", inputDate)

                }, mYear, mMonth, mDay

            )

            datePickerDialog.show()
        }
    }

    private fun createTimePickerButton() {

        btnTimePicker = requireView().findViewById(R.id.btn_time)

        btnTimePicker.setOnClickListener {
            var calendar: Calendar = Calendar.getInstance()

            mHour = calendar.get(Calendar.HOUR_OF_DAY)
            mMinute = calendar.get(Calendar.MINUTE)
            var time = calendar.time

            val timePickerDialog = TimePickerDialog(
                appContext, TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                    val inputTime = String.format("%02d:%02d", hour, minute)
                    txtTime?.setText(inputTime)
                    viewModel.data.value?.put("time", inputTime)



                }, mHour, mMinute, true
            )
            viewModel.data.value?.put("time", calendar)


            timePickerDialog.show()
        }
    }

    private fun createSearchButton() {

        btnSearch = requireView().findViewById(R.id.btn_search)


        btnSearch.setOnClickListener {

            // On verifie si tous les champs ont été renseignés"
            if (allValuesAreSelected() && downloadJob.isCompleted) {

                fragmentListener.addFragment(2)

            } else {

                var message = "Veuillez selectionner tous les champs,"
                Toast.makeText(appContext, message, Toast.LENGTH_LONG).show()
            }


        }
    }

    private fun allValuesAreSelected(): Boolean {
        if(viewModel.data.value?.get("date") == null)
            return false
        if(viewModel.data.value?.get("time") == null)
            return false
        if(viewModel.data.value?.get("bus") == null)
            return false
        if (viewModel.data.value?.get("direction") == null)
            return false

        return true

    }

    /**
     * Fonction qui appel la methode du main activity qui permet de relancer la notif au bout de certain temps.
     *
     */
    private fun startRepeatingNotificationJob(timeInterval: Long, downloadJob: Job): Job {

        return fragmentListener.startNotificationJob(timeInterval,downloadJob)

    }


    /**
     * Fonction declenchee une fois la base a ete remplie, elle alimente le spinner des lignes de bus
     */
    private suspend fun populateBusSpinner() {

        withContext(Dispatchers.Main) {
            progressBarText.text = "Alimentation du spinner"
        }


        var routes = appDatabase.routes().getAllRoutes()


        busList.clear()

        busList.addAll(routes)

        // On notifie le spinner depuis le main thread
        if(this.isAdded){
            requireActivity().runOnUiThread {
                spinnerBusAdapter.notifyDataSetChanged()
            }
        }

        withContext(Dispatchers.Main) {
            progressBarText.visibility = View.GONE
            progressBar.visibility = View.GONE
        }


        Log.i("SPINNER", "Finished populating spinner")
    }

    /**
     * Fonction declenchee à chaques fois qu'une ligne est selectionnée, elle alimente le spinner
     * des directions de cette ligne.
     */
    private fun populateDirectionSpinner(route: RoutesEntity) {



        if (route != null) {


            var directions = this.appDatabase.trips().getTripDirectionFromRoute(route.id)

            if(directions.size == 2){
                // Cas exceptionnel (par ex si on fait group by sur direction_id sur la ligne C7 où ca renvoie la meme direction)
                if(directions[0] == directions[1]){
                    directions = this.appDatabase.trips().getAllTripDirectionFromRoute(route.id)
                }
            }


            directionList.clear()
            directionList.addAll(directions)


            // On notifie le spinner
            requireActivity().runOnUiThread {
                spinnerDirectionAdapter.notifyDataSetChanged()
                spinnerDirection.setSelection(0)

                // On alimente le map avec la valeur par defaut
                viewModel.data.value?.put("direction", spinnerDirection.selectedItem)


            }
        }
    }


    /**
     * Definie les actions des spinners
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (parent != null && !downloadJob.isActive) {

            // Si le spinner s'agit du spinner des lignes, on obtient l'entite et on alimente le spinner des directions
            if (parent.id == R.id.spinner_bus) {

                val selectedRoute = parent?.getItemAtPosition(position) as RoutesEntity

                GlobalScope.launch() {
                    populateDirectionSpinner(selectedRoute)
                }

                // On alimente le map
                viewModel.data.value?.put("bus", selectedRoute)
                viewModel.data.value?.put("busSelection", position)



            } else if (parent.id == R.id.spinner_direction) {

                val selectedDirection = parent?.getItemAtPosition(position) as String

                // On alimente le map
                viewModel.data.value?.put("direction", selectedDirection)
                viewModel.data.value?.put("directionSelection", position)



            }
        }

    }


    override fun onNothingSelected(parent: AdapterView<*>?) {

        if (parent != null && !downloadJob.isActive) {
            // Si le spinner s'agit du spinner des lignes, on obtient l'entite et on alimente le spinner des directions
            var message = "Attendez le remplissage de la base"
            Toast.makeText(appContext, message, Toast.LENGTH_LONG).show()
        }
    }



}