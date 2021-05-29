package com.example.diplomskitest1.ui.main

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.diplomskitest1.models.Artikal
import java.security.cert.X509Certificate
import javax.net.ssl.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import android.R.string.no
import android.R.attr.name
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.diplomskitest1.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.example.diplomskitest1.models.ArtikalWDetails
import com.example.diplomskitest1.models.VolleyCallback
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel


    private val artikli: MutableLiveData<List<ArtikalWDetails>> by lazy {
        MutableLiveData<List<ArtikalWDetails>>().also {
            loadArtikli()
        }
    }


    fun getArtikli():LiveData<List<ArtikalWDetails>>
    {
        return  artikli;
    }
    private fun loadArtikli()
    {
        //asinhrono loadovanje artikala
        NetworkManager.getInstance().getRequest(object : VolleyCallback {
            override fun onSuccess(result: String) {
                val gson = Gson()
                /*
                val myType = object : TypeToken<List<Artikal>>() {}.type
              val   artikliPars= gson.fromJson<List<Artikal>>(br, myType)*/


                val sviArtikli = gson.fromJson(result,Array<ArtikalWDetails>::class.java).toList()



                artikli.postValue(sviArtikli)
            }
        },NetworkManager.REQUEST.ARTIKLI)
    }

}

