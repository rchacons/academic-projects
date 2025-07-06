package fr.istic.mob.starcn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    val data = MutableLiveData<HashMap<String,Any>>()

    fun setData(data: HashMap<String,Any>) {
        this.data.value = data
    }
}
