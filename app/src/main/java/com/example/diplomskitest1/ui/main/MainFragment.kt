package com.example.diplomskitest1.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.diplomskitest1.models.ArtikalNaruzbe
import com.example.diplomskitest1.models.ArtikalWDetails
import com.example.diplomskitest1.network.NetworkManager
import com.example.diplomskitest1.recycler_view.RecyclerViewAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import java.net.InetAddress


class MainFragment() : Fragment() {

    var pager:ViewPager2?=null
    var cijena = 0.0f
    companion object {
        fun newInstance() = MainFragment()
        var artikliNarudzbe= mutableListOf<ArtikalNaruzbe>()

        fun getCijena():Float
        {
            var cijena = 0.0f
            for(artikal in artikliNarudzbe)
            {
                cijena+=artikal.cijena.cpCijena
            }
            return cijena
        }
    }
    constructor(pager:ViewPager2) :this(){
        this.pager = pager

    }
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?



    ): View {
        return inflater.inflate(com.example.diplomskitest1.R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        NetworkManager.getInstance(this.requireContext())
        updateCijena()

        var recyclerView=recycler_artikli
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
           // TODO: Use the ViewModel'


        val model: MainViewModel by this.viewModels()
        model.getArtikli().observe(viewLifecycleOwner, Observer<List<ArtikalWDetails>>{ artikli ->




            var adapter = RecyclerViewAdapter(artikli,context,this)

            recyclerView!!.adapter=adapter

            recyclerView!!.layoutManager = LinearLayoutManager(context)

        })

        button.setOnClickListener(View.OnClickListener { dovrsiNarudzbu() })

    }
    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr: InetAddress = InetAddress.getByName("google.com")
            //You can replace it with your name
            !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }
    fun addItemToList(artikal:ArtikalNaruzbe)
    {
        artikliNarudzbe.add(artikal)
        cijena+= artikal.cijena.cpCijena
        tvCijenaNarudzbe.text = "Za platiti " + cijena + " KM"

    }
    fun updateCijena()
    {

        tvCijenaNarudzbe.text = "Za platiti " + getCijena() + " KM"

    }


    fun dovrsiNarudzbu()
    {
        pager!!.currentItem=1
    }


}
