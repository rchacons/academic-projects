package fr.istic.mob.starcn

import android.app.*
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import fr.istic.mob.starcn.database.AppDatabase
import fr.istic.mob.starcn.database.entity.RoutesEntity
import fr.istic.mob.starcn.fragments.*
import fr.istic.mob.starcn.fragments.adapters.SpinnerAdapter
import kotlinx.coroutines.*
import java.util.AbstractMap.SimpleEntry


class MainActivity : AppCompatActivity(),  FragmentListener {

    // Le view model qui va retenir les donnees
    private lateinit var viewModel: MyViewModel

    // Variables du service de telechargement
    private lateinit var notificationJob: Job
    private lateinit var fileDownloader: FileDownloader
    private var link =
        "https://data.explore.star.fr/explore/dataset/tco-busmetro-horaires-gtfs-versions-td/download/?format=csv&timezone=Europe/Berlin&lang=fr&use_labels_for_header=true&csv_separator=%3B"


    private var isTablet = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // On verifie si c'est une tablet
        isTablet = resources.configuration.smallestScreenWidthDp >= 600


        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        // Si c'est le demmarrage, on lance le premier fragment
        if (savedInstanceState == null) {
            addFragment(1)

        }
        // Sinon on affiche le reste
        else {
            if (supportFragmentManager.backStackEntryCount >= 2) {
                val secondSublayout: FrameLayout = findViewById(R.id.second_sublayout)
                secondSublayout.visibility = View.VISIBLE
                hideSearchFragment()

                if(isTablet && supportFragmentManager.backStackEntryCount >= 3){
                    val thirdSublayout: FrameLayout = findViewById(R.id.third_sublayout)
                    thirdSublayout.visibility = View.VISIBLE
                }
            }
        }
        fileDownloader = FileDownloader(this, viewModel)

    }

    private fun hideSearchFragment() {
        val searchFragment = supportFragmentManager.findFragmentById(R.id.search_fragment)
        if (searchFragment != null) {
            supportFragmentManager.beginTransaction()
                .hide(searchFragment)
                .commit()
        }

        val searchFragmentLand = supportFragmentManager.findFragmentById(R.id.search_fragment_land)
        if (searchFragmentLand != null) {
            supportFragmentManager.beginTransaction()
                .hide(searchFragmentLand)
                .commit()
        }


    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        // Si on est ici c'est parce que la notification a ete appuyer, donc on telecharge la nouvelle base
        if (intent != null) {

            try {
                if(viewModel.data.value?.get("notificationJob") != null){
                    notificationJob = viewModel.data.value?.get("notificationJob") as Job
                }
                notificationJob.cancel()

                var newBase =
                    intent.getSerializableExtra("baseValue") as SimpleEntry<String, String>

                viewModel.data.value?.put("baseValue", newBase)

                addFragment(1)


            } catch (e: Exception) {
                // Si on a pas pu remplir la nouvelle base, on relance la notification
                startNotificationJob(15000, Job())
                Log.e("INTENT", "Erreur lors du remplissage de la nouvelle base $e")
            }
        }
    }

    /**
     * Fonction qui cree la notification et la configure pour envoyer la nouvelle base une fois
     * on a clicke dessus.
     */
    private fun createNotif(newBase: SimpleEntry<String, String>) {

        val channelId = "i.apps.notifications"

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        var notificationChannel =
            NotificationChannel(channelId, "File downloader", NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationChannel.enableVibration(false)
        notificationManager.createNotificationChannel(notificationChannel)


        val resultIntent = Intent(this, MainActivity::class.java)
        // On met les informations de la nouvelle base dans l'intent
        resultIntent.putExtra("baseValue", newBase)
        resultIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        val resultPendingIntent =
            PendingIntent.getActivity(this, 0, resultIntent, FLAG_UPDATE_CURRENT or FLAG_MUTABLE)

        // On cree le builder
        val mBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_menu_more)
            .setContentTitle("Nouveau fichier disponible: ${newBase.key} ")
            .setContentText("Clickez ici pour le telecharger !")
            .setAutoCancel(true)
            .setContentIntent(resultPendingIntent)

        // On lance la notification
        notificationManager.notify(123456, mBuilder.build())

    }



    override fun onBackPressed() {


        if (supportFragmentManager.backStackEntryCount == 2) {
            // On cache le deuxieme sub layout
            val second_sublayout: FrameLayout = findViewById(R.id.second_sublayout)
            second_sublayout.visibility = View.GONE

            // On reaffiche le search fragment
            val searchFragment = supportFragmentManager.findFragmentById(R.id.search_fragment)
            if (searchFragment != null) {
                supportFragmentManager.beginTransaction()
                    .show(searchFragment)
                    .commit()
            }

            // On reaffiche le search fragment si c'est le cas d'une orientation horizontal
            val searchFragmentLand =
                supportFragmentManager.findFragmentById(R.id.search_fragment_land)
            if (searchFragmentLand != null) {
                supportFragmentManager.beginTransaction()
                    .show(searchFragmentLand)
                    .commit()
            }
        } else if (supportFragmentManager.backStackEntryCount == 3) {
            // On cache le deuxieme sub layout
            val thirdSublayout: FrameLayout = findViewById(R.id.third_sublayout)
            thirdSublayout.visibility = View.GONE
        }


        super.onBackPressed()
    }


    /**
     * Ajout les fragments en fonction de son nb. Elle gere le deplacement des fragments entre les
     * sub layouts dans les cas d'une tablet ou un telephone.
     */
    override fun addFragment(fragmentNumber: Int) {

        // On gere les 3 layouts quand il s'agit d'une tablet
        if (isTablet) {
            when (fragmentNumber) {
                1 -> {

                    val count : Int = supportFragmentManager.backStackEntryCount
                    for(i in 1..count){
                        supportFragmentManager.popBackStackImmediate()
                    }
                    val secondSublayout: FrameLayout = findViewById(R.id.second_sublayout)
                    secondSublayout.visibility = View.GONE

                    val thirdSubLayout: FrameLayout = findViewById(R.id.third_sublayout)
                    thirdSubLayout.visibility = View.GONE


                    val firstFragment = FirstFragment();

                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.enter_from_right,
                            R.anim.exit_to_left,
                            R.anim.enter_from_left,
                            R.anim.exit_to_right
                        )
                        .replace(R.id.first_sublayout, firstFragment)
                        .addToBackStack(null)
                        .commit()

                    viewModel.data.value?.put("number", 1)
                }
                2 -> {

                    hideSearchFragment()

                    // Dans le cas ou on click sur le fragment a gauche
                    if(supportFragmentManager.backStackEntryCount == 3){
                        supportFragmentManager.popBackStack()
                        supportFragmentManager.popBackStack()

                        val thirdSubLayout: FrameLayout = findViewById(R.id.third_sublayout)
                        thirdSubLayout.visibility = View.GONE
                    }
                    else if (supportFragmentManager.backStackEntryCount == 2) {
                        supportFragmentManager.popBackStack()
                    }



                    val secondFragment = SecondFragment()

                    val secondSublayout: FrameLayout = findViewById(R.id.second_sublayout)
                    secondSublayout.visibility = View.VISIBLE

                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.enter_from_right,
                            R.anim.exit_to_left,
                            R.anim.enter_from_left,
                            R.anim.exit_to_right
                        )
                        .replace(R.id.second_sublayout, secondFragment)
                        .addToBackStack(null)
                        .commit()

                    viewModel.data.value?.put("number", 2)
                }

                3 -> {


                    // Le fragment a ajouter
                    val thirdFragment = ThirdFragment()

                    val thirdSubLayout: FrameLayout = findViewById(R.id.third_sublayout)
                    thirdSubLayout.visibility = View.VISIBLE

                    // Dans le cas ou on click sur le fragment a gauche
                    if(supportFragmentManager.backStackEntryCount == 4){
                        onBackPressed()
                    }
                    if (supportFragmentManager.backStackEntryCount == 3) {

                        supportFragmentManager.popBackStack()


                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(
                                R.anim.enter_from_right,
                                R.anim.exit_to_left,
                                R.anim.enter_from_left,
                                R.anim.exit_to_right
                            )
                            .replace(R.id.third_sublayout, thirdFragment)
                            .addToBackStack(null)
                            .commit()
                    }
                    // Sinon, on ajoute les fragments normalement
                    else {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(
                                R.anim.enter_from_right,
                                R.anim.exit_to_left,
                                R.anim.enter_from_left,
                                R.anim.exit_to_right
                            )
                            .setReorderingAllowed(true)
                            .replace(R.id.third_sublayout, thirdFragment)
                            .addToBackStack(null)
                            .commit()

                    }

                    viewModel.data.value?.put("number", 3)
                }
                4 -> {

                    val myNewFragment2 = SecondFragment()

                    // On cree un troisime fragment temporaire
                    val myNewFragment3 = ThirdFragment()

                    // Le fragment a ajouter
                    val myNewFragment4 = FourthFragment()

                    // Dans le cas ou on click sur le fragment a gauche
                    if (supportFragmentManager.backStackEntryCount > 3) {
                        supportFragmentManager.popBackStack()

                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .add(R.id.first_sublayout, myNewFragment2)
                            .add(R.id.second_sublayout, myNewFragment3)
                            .setCustomAnimations(
                                R.anim.enter_from_right,
                                R.anim.exit_to_left,
                                R.anim.enter_from_left,
                                R.anim.exit_to_right
                            )
                            .replace(R.id.third_sublayout, myNewFragment4)
                            .addToBackStack(null)
                            .commit()

                    }
                    // Sinon on l'ajout normalement
                    else {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(
                                R.anim.enter_from_right,
                                R.anim.exit_to_left,
                                R.anim.enter_from_left,
                                R.anim.exit_to_right
                            )
                            .setReorderingAllowed(true)
                            .replace(R.id.third_sublayout, myNewFragment4)
                            .replace(R.id.second_sublayout, myNewFragment3)
                            .replace(R.id.first_sublayout, myNewFragment2)
                            .addToBackStack(null)
                            .commit()

                    }

                    viewModel.data.value?.put("number", 4)

                }
                5 -> {

                    // On verifie si le dernier fragment est aussi le 5e
                    val fm = supportFragmentManager
                    val lastFragEntry = fm.backStackEntryCount - 1
                    val lastFragTag = fm.getBackStackEntryAt(lastFragEntry).name


                    if (lastFragTag == "fifth") {
                        supportFragmentManager.popBackStack()
                    }


                    val fifthFragment = FifthFragment()

                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.enter_from_right,
                            R.anim.exit_to_left,
                            R.anim.enter_from_left,
                            R.anim.exit_to_right
                        )
                        .replace(R.id.first_sublayout, fifthFragment, "fifth")
                        .addToBackStack("fifth")
                        .commit()
                }
            }
        }
        // Sinon on s'occupe juste de deux layouts
        else {
            when (fragmentNumber) {
                1 -> {
                    val count : Int = supportFragmentManager.backStackEntryCount
                    for(i in 1..count){
                        supportFragmentManager.popBackStackImmediate()
                    }

                    val secondSublayout: FrameLayout = findViewById(R.id.second_sublayout)
                    secondSublayout.visibility = View.GONE

                    val firstFragment = FirstFragment();

                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.enter_from_right,
                            R.anim.exit_to_left,
                            R.anim.enter_from_left,
                            R.anim.exit_to_right
                        )
                        .replace(R.id.first_sublayout, firstFragment)
                        .addToBackStack(null)
                        .commit()

                    viewModel.data.value?.put("number", 1)
                }
                2 -> {

                    hideSearchFragment()
                    // Dans le cas ou on click sur le fragment a gauche
                    if (supportFragmentManager.backStackEntryCount > 1) {
                        supportFragmentManager.popBackStack()
                    }

                    val secondFragment = SecondFragment()

                    val secondSublayout: FrameLayout = findViewById(R.id.second_sublayout)
                    secondSublayout.visibility = View.VISIBLE

                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.enter_from_right,
                            R.anim.exit_to_left,
                            R.anim.enter_from_left,
                            R.anim.exit_to_right
                        )
                        .replace(R.id.second_sublayout, secondFragment)
                        .addToBackStack(null)
                        .commit()

                    viewModel.data.value?.put("number", 2)
                }

                3 -> {

                    // On cree un deuxieme fragment temporaire
                    val myNewFragment = SecondFragment()

                    // Le fragment a ajouter
                    val thirdFragment = ThirdFragment()

                    // Dans le cas ou on click sur le fragment a gauche
                    if (supportFragmentManager.backStackEntryCount > 2) {

                        supportFragmentManager.popBackStack()

                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .add(R.id.first_sublayout, myNewFragment)
                            .setCustomAnimations(
                                R.anim.enter_from_right,
                                R.anim.exit_to_left,
                                R.anim.enter_from_left,
                                R.anim.exit_to_right
                            )
                            .replace(R.id.second_sublayout, thirdFragment)
                            .addToBackStack(null)
                            .commit()
                    }
                    // Sinon, on ajoute les fragments normalement
                    else {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(
                                R.anim.enter_from_right,
                                R.anim.exit_to_left,
                                R.anim.enter_from_left,
                                R.anim.exit_to_right
                            )
                            .setReorderingAllowed(true)
                            .replace(R.id.second_sublayout, thirdFragment)
                            .replace(R.id.first_sublayout, myNewFragment)
                            .addToBackStack(null)
                            .commit()

                    }

                    viewModel.data.value?.put("number", 3)
                }
                4 -> {

                    // On cree un troisime fragment temporaire
                    val myNewFragment = ThirdFragment()

                    // Le fragment a ajouter
                    val fourthFragment = FourthFragment()

                    // Dans le cas ou on click sur le fragment a gauche
                    if (supportFragmentManager.backStackEntryCount > 3) {
                        supportFragmentManager.popBackStack()

                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .add(R.id.first_sublayout, myNewFragment)
                            .setCustomAnimations(
                                R.anim.enter_from_right,
                                R.anim.exit_to_left,
                                R.anim.enter_from_left,
                                R.anim.exit_to_right
                            )
                            .replace(R.id.second_sublayout, fourthFragment)
                            .addToBackStack(null)
                            .commit()

                    }
                    // Sinon on l'ajout normalement
                    else {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(
                                R.anim.enter_from_right,
                                R.anim.exit_to_left,
                                R.anim.enter_from_left,
                                R.anim.exit_to_right
                            )
                            .setReorderingAllowed(true)
                            .replace(R.id.second_sublayout, fourthFragment)
                            .replace(R.id.first_sublayout, myNewFragment)
                            .addToBackStack(null)
                            .commit()

                    }

                    viewModel.data.value?.put("number", 4)

                }
                5 -> {

                    // On verifie si le dernier fragment est aussi le 5e
                    val fm = supportFragmentManager
                    val lastFragEntry = fm.backStackEntryCount - 1
                    val lastFragTag = fm.getBackStackEntryAt(lastFragEntry).name


                    if (lastFragTag == "fifth") {
                        supportFragmentManager.popBackStack()
                    }


                    val fifthFragment = FifthFragment()

                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.enter_from_right,
                            R.anim.exit_to_left,
                            R.anim.enter_from_left,
                            R.anim.exit_to_right
                        )
                        .replace(R.id.first_sublayout, fifthFragment, "fifth")
                        .addToBackStack("fifth")
                        .commit()
                }
            }

        }


    }

    override fun startNotificationJob(timeInterval: Long, downloadJob: Job): Job {


        notificationJob = CoroutineScope(Dispatchers.Default).launch {
            while (NonCancellable.isActive) {

                downloadJob.join()

                delay(timeInterval)

                var newBase = fileDownloader.asyncVerifyNewFile(link)

                // On a trouve un nouveau fichier
                if (newBase != null) {
                    //On cree la notification
                    Log.i("NEW BASE", "New Base found: ${newBase.key} , ${newBase.value}")
                    createNotif(newBase)
                } else {
                    Log.i("NEW BASE", "No new base was found.")

                }

            }
        }

        viewModel.data.value?.put("notificationJob",notificationJob)
        return notificationJob
    }

}