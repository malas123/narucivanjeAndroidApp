package com.example.diplomskitest1.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diplomskitest1.helper.PrefsHelper
import java.time.OffsetDateTime
import com.example.diplomskitest1.models.NarudzbaStanje
import com.example.diplomskitest1.models.VolleyCallback
import com.example.diplomskitest1.network.NetworkManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class FragmentNarudzbeViewModel : ViewModel() {
    private val narudzbe: MutableLiveData<List<NarudzbaStanje>> by lazy {
        MutableLiveData<List<NarudzbaStanje>>().also {
            loadNarudzbe()
        }
    }


    fun getNarudzbe(): LiveData<List<NarudzbaStanje>>
    {
        return  narudzbe;
    }
    private fun loadNarudzbe()
    {
        //asinhrono loadovanje artikala
        NetworkManager.getInstance().getRequest(object : VolleyCallback {
            override fun onSuccess(result: String) {
                val gson = Gson()
                val itemType = object : TypeToken<List<NarudzbaStanje>>() {}.type
                val sveNarudzbe = gson.fromJson<List<NarudzbaStanje>>(result, itemType)
                narudzbe.postValue(sveNarudzbe)
            }
        }, NetworkManager.REQUEST.NARUDZBE,PrefsHelper.getIdNarucioca().toString())
    }
    fun refresh()
    {
        loadNarudzbe()
    }
}