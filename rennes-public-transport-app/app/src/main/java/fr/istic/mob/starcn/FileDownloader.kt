package fr.istic.mob.starcn

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import fr.istic.mob.starcn.database.AppDatabase
import fr.istic.mob.starcn.database.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.*
import java.net.URL
import java.util.AbstractMap.SimpleEntry
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


class FileDownloader(context: Context, viewModel: MyViewModel) : Serializable {

    private var appContext = context as Activity
    private var acceptedFiles =
        arrayOf("calendar.txt", "routes.txt", "stops.txt", "stop_times.txt", "trips.txt")
    private var database = AppDatabase.getDatabase(appContext)

    // Pair contenant l'id et l'url de la base actuelle
    private var actualBase : SimpleEntry<String, String> = SimpleEntry("","")

    lateinit var progressBarText : TextView
    private var myViewModel = viewModel


    /**
     * Fonction qui lance un coroutine asynchrone pour telecharger un fichier contenu dans un URL
     * @param fileEntry: le pair contenant id et l'url
     */
    suspend fun asyncDownloadFileFromWeb(url:String) {

        // Text view pour mettre a jour le contenu de la barre de progression
        progressBarText = appContext
            .findViewById(R.id.txt_progress_bar) as TextView

        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities)


        //On teste la connexion internet
        if (actNw != null && actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {

            withContext(Dispatchers.IO) {

                // D'abord on telecharge le fichier CSV
                var urlMap = downloadCSVFileFromUrl(url)

                // Si dans le fichier on trouve des elements, on telecharge le dernier zip de la liste (le premier sorti) et on garde sa reference
                if (urlMap.isNotEmpty()) {

                    var lastEntry = urlMap.entries.last()

                    actualBase = SimpleEntry(lastEntry.key,lastEntry.value)
                    Log.i("INFO","Saving actual database key: ${actualBase.key}")
                    myViewModel.data.value?.put("oldBase",actualBase)

                    downloadZipFileFromBaseUrl(actualBase)
                }
            }
        } else {
            Log.e("download", "Connexion réseau indisponible.")
        }

    }

    /**
     * Fonction qui telecharge le fichier csv contenu dans l'url
     * @param myURL: url du fichier csv
     * @return Map: contenant l'id et l'url des fichiers
     */
    private suspend fun downloadCSVFileFromUrl(myURL: String): Map<String, String> {

        var urlMap: LinkedHashMap<String, String> = linkedMapOf()

        try {


            var url = URL(myURL)
            // On telecharge le fichier
            url.openStream().use { input ->
                FileOutputStream(
                    File(
                        appContext.applicationContext.filesDir,
                        "horairesStar.csv"
                    )
                ).use { output ->
                    input.copyTo(output)
                }
            }

            // On recupere le fichier pour le parcourir
            var csvFile = File(appContext.applicationContext.filesDir, "horairesStar.csv")

            val bufferedReader = File(csvFile.path).bufferedReader()

            // Le csv parser nous permettra de traiter les donnes de la premiere ligne comme des colonnes
            val csvParser = CSVParser(
                bufferedReader, CSVFormat.newFormat(';')
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim()
            );


            for (csvRecord in csvParser) {

                var id = csvRecord.get("ID")
                var url = csvRecord.get("URL")

                urlMap.put(id, url)

            }

            // On supprime le fichier
            csvFile.delete()

        } catch (e: Exception) {
            Log.e("download", "Error reading it $e")
        }

        return urlMap
    }

    /**
     * Fonction qui telecharge le fichier zip contenu dans l'url et l'enregistre dans un repertoire
     * local.
     * @param baseInfo: pair de <id,url> de la base a telecharger
     */
    suspend fun downloadZipFileFromBaseUrl(baseInfo : SimpleEntry<String,String>) {

        try {
            withContext(Dispatchers.Main) {
                progressBarText.text = "Telechargement du fichier Zip"
            }

            var url = URL(baseInfo.value)

            // On telecharge le fichier
            url.openStream().use { input ->
                FileOutputStream(
                    File(
                        appContext.applicationContext.filesDir,
                        "base.zip"
                    )
                ).use { output ->
                    input.copyTo(output)
                }
            }

            // On le recupere et decompresse
            val zipFile = File(appContext.applicationContext.filesDir, "base.zip")

            unpackZip(zipFile)

            // On transforme les fichiers decompresses en donnes pour la bdd
            transformFilesToDatabase(appContext.applicationContext.filesDir.toString())

        } catch (e: Exception) {
            Log.e("download", "Error reading it $e")
        }

    }

    /**
     * Fonction qui permet de decompresser un fichier zip
     * @param file : le fichier zip en question
     */
    private suspend fun unpackZip(file: File) {

        try {
            withContext(Dispatchers.Main) {
                progressBarText.text = "Extraction du fichier"
            }

            var zipFile = file
            var parentFolder = zipFile.parentFile!!.path
            var filename = ""

            var inputStream = FileInputStream(zipFile.path)
            var zipInputStream = ZipInputStream(BufferedInputStream(inputStream))

            val buffer = ByteArray(1024)
            var count: Int

            var zipEntry: ZipEntry? = zipInputStream.nextEntry

            // Pour chaque fichier de notre zip
            while (zipEntry != null) {

                filename = zipEntry.name

                // On sauvegarde le fichier dans le dossier parent
                val fileOutputStream = FileOutputStream("$parentFolder/$filename")

                Log.i("ZIP", "Unzipping $filename")
                while (zipInputStream.read(buffer).also { count = it } != -1) {
                    fileOutputStream.write(buffer, 0, count)
                }

                fileOutputStream.close()


                zipInputStream.closeEntry()
                zipEntry = zipInputStream.nextEntry
            }

            // On supprime le fichier zip
            zipFile.delete()

            zipInputStream.close()

        } catch (e: IOException) {
            e.printStackTrace();
        }
    }

    /**
     * Fonction qui transforme les fichiers txt et les enregistre en bdd
     * @param filesPath : chemin du dossier qui contient les fichiers à traiter
     */
    private suspend fun transformFilesToDatabase(filesPath: String) {

        withContext(Dispatchers.Main) {
            progressBarText.text = "Remplissage de la base"
        }

        // On efface le contenu de la base
        database.trips().deleteAllTrips()
        database.stops().deleteAllStops()
        database.routes().deleteAllRoutes()
        database.stopsTime().deleteAllStopTimes()
        database.calendar().deleteAllCalendars()

        Log.i("INFO", "Database cleared")
        // D'abord on parcourt tous les fichiers du chemin
        File(filesPath).walk().forEach {


            // On va traiter seulement ceux qui nous interessent
            if (acceptedFiles.contains(it.name)) {

                val bufferedReader = File(it.path).bufferedReader()

                // Le csv parser nous permettra de traiter les donnes de la premiere ligne comme des colonnes
                val csvParser = CSVParser(
                    bufferedReader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim()
                );

                this.database.runInTransaction(Runnable {
                    when (it.nameWithoutExtension) {
                        "routes" -> {

                            for (csvRecord in csvParser) {
                                val routesEntity = RoutesEntity(
                                    csvRecord.get("route_id"),
                                    csvRecord.get("route_short_name"),
                                    csvRecord.get("route_long_name"),
                                    csvRecord.get("route_desc"),
                                    csvRecord.get("route_type"),
                                    csvRecord.get("route_color"),
                                    csvRecord.get("route_text_color")
                                )

                                this.database.routes().insert(routesEntity)
                            }

                        }
                        "calendar" -> {
                            for (csvRecord in csvParser) {
                                val calendarEntity = CalendarEntity(
                                    csvRecord.get("service_id") ,
                                    csvRecord.get("monday"),
                                    csvRecord.get("tuesday"),
                                    csvRecord.get("wednesday"),
                                    csvRecord.get("thursday"),
                                    csvRecord.get("friday"),
                                    csvRecord.get("saturday"),
                                    csvRecord.get("sunday"),
                                    csvRecord.get("start_date"),
                                    csvRecord.get("end_date")
                                )
                                this.database.calendar().insert(calendarEntity)
                            }
                        }
                        "stops" -> {
                            for (csvRecord in csvParser) {
                                val stopsEntity = StopsEntity(
                                    csvRecord.get("stop_id"),
                                    csvRecord.get("stop_name"),
                                    csvRecord.get("stop_desc"),
                                    csvRecord.get("stop_lat"),
                                    csvRecord.get("stop_lon"),
                                    csvRecord.get("wheelchair_boarding")
                                )
                                this.database.stops().insert(stopsEntity)
                            }
                        }
                        "stop_times" -> {

                            var listStopTimes = ArrayList<StopsTimeEntity>()

                            for (csvRecord in csvParser) {

                                listStopTimes.add(
                                    StopsTimeEntity(
                                        0,
                                        csvRecord.get("trip_id"),
                                        csvRecord.get("arrival_time"),
                                        csvRecord.get("departure_time"),
                                        csvRecord.get("stop_id"),
                                        csvRecord.get("stop_sequence")
                                    )
                                )
                            }
                            this.database.stopsTime().insertAllStopsTime(listStopTimes)
                        }
                        "trips" -> {
                            for (csvRecord in csvParser) {
                                this.database.trips().insert(
                                    TripsEntity(
                                        csvRecord.get("trip_id"),
                                        csvRecord.get("route_id"),
                                        csvRecord.get("service_id"),
                                        csvRecord.get("trip_headsign"),
                                        csvRecord.get("direction_id"),
                                        csvRecord.get("block_id"),
                                        csvRecord.get("wheelchair_accessible")
                                    )
                                )
                            }
                        }
                    }

                })
            }
            // On supprime le fichier pour economiser le space du disque
            it.delete()
        }
    }

    /**
     * Fonction qui verifie s'il y a un nouveau fichier (nouvelle base) disponible pour telecharger
     * @param url : L'URL du fichier a telecharger
     * @return SimpleEntry<String,String> : le pair <ID,URL> du nouveau fichier
     */
    suspend fun asyncVerifyNewFile(url: String): SimpleEntry<String,String>? {

        var newBase : SimpleEntry<String,String>? = null

        try {

            var oldBase = myViewModel.data.value?.get("oldBase")

            if(oldBase != null){
                actualBase = oldBase as SimpleEntry<String,String>
            }


            withContext(Dispatchers.IO) {
                var newMap = downloadCSVFileFromUrl(url)

                // On parcourt le map et compare la reference avec celle sauvergade avant
                if (newMap.isNotEmpty()) {

                    var iterator = newMap.iterator()

                    while (iterator.hasNext()) {

                        var base = iterator.next()

                        // Si la reference n'est pas la meme de la base actuelle
                        if (base.key != actualBase.key) {
                            newBase = SimpleEntry(base.key,base.value)

                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("download", "Error reading the CSV file $e")
        }
        return newBase

    }

}