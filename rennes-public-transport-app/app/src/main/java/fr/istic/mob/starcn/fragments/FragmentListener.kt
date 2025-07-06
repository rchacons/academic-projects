package fr.istic.mob.starcn.fragments

import fr.istic.mob.starcn.FileDownloader
import kotlinx.coroutines.Job

interface FragmentListener {
    /**
     * Affiche le fragment et l'ajout dans le backstack
     * @param fragmentNumber: Le nb du fragment a ajouter
     */
    fun addFragment(fragmentNumber: Int)

    /**
     * Verifie s'il existe un nouveau fichier et si c'est le cas lance une notification pour le telecharger
     */
    fun startNotificationJob(timeInterval: Long, downloadJob: Job) : Job


}