package com.example.diplomskitest1.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler

import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.diplomskitest1.R
import com.example.diplomskitest1.helper.PrefsHelper
import com.example.diplomskitest1.models.DTOUlogovanNarucilac
import com.example.diplomskitest1.models.Korisnik
import com.example.diplomskitest1.models.NarudzbaStanje
import com.example.diplomskitest1.models.VolleyCallback
import com.example.diplomskitest1.network.NetworkManager
import com.example.diplomskitest1.recycler_view.RecyclerViewAdapter
import com.example.diplomskitest1.recycler_view.RecyclerViewNarudzbeNarucioca
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_narudzbe.*
import kotlinx.android.synthetic.main.fragment_narudzbe.view.*


class FragmentNarudzbe(): Fragment() {
    var pager:ViewPager2?=null
    var isCreated = false
    var modelVM: FragmentNarudzbeViewModel?=null
    companion object
    {
        fun newInstance() = FragmentNarudzbe

    }

    constructor(pager:ViewPager2) :this(){
        this.pager = pager

    }

    private lateinit var viewModel: FragmentNarudzbeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_narudzbe, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_narudzbe_refresh.setOnClickListener(View.OnClickListener { refresh()})
        isCreated = true
       refreshState()

    }
    fun refreshState()
    {
        if(!isCreated)
            return
        if(PrefsHelper.getKorisnikUlogovan())
        {
            //Toast.makeText(context,"Morate biti ulogovani za pregled prethodnih narudzbi!",Toast.LENGTH_LONG).show()
            fragment_narudzbe.rvNarudzbeNarucioca.visibility = View.VISIBLE
            fragment_narudzbe.lin_layout_login.visibility = View.GONE
            startObserving()

        }
        else
        {
            fragment_narudzbe.rvNarudzbeNarucioca.visibility = View.GONE
            fragment_narudzbe.lin_layout_login.visibility = View.VISIBLE
            btn_login.setOnClickListener(View.OnClickListener { btnLogin() })
            btn_registracija.setOnClickListener(View.OnClickListener { btnReg() })
        }

    }
    fun startObserving()
    {


        viewModel = ViewModelProviders.of(this).get(FragmentNarudzbeViewModel::class.java)
        //use the view model
        val model: FragmentNarudzbeViewModel by viewModels()
        this.modelVM = model
        model.refresh()
        model.getNarudzbe().observe(viewLifecycleOwner, Observer<List<NarudzbaStanje>>{ narudzbe ->
            // update UI

            var adapter = RecyclerViewNarudzbeNarucioca(narudzbe.toMutableList())

            rvNarudzbeNarucioca!!.adapter=adapter

            rvNarudzbeNarucioca!!.layoutManager = LinearLayoutManager(context)
        })


    }


    fun refresh()
    {
        if(modelVM!=null)
        {
            modelVM!!.refresh()
           // startObserving()

        }
    }

    fun btnLogin()
    {
        val gson = Gson()

        val username = et_login_username.text.toString()
        val password = et_login_pw.text.toString()
        val korisnik = Korisnik(username,password)
        var korisnikJson = gson.toJson(korisnik)

        NetworkManager.getInstance().postRequestWithCallback(object :VolleyCallback
        {
            override fun onSuccess(result: String) {

                var ulogovaniNarucilac : DTOUlogovanNarucilac = gson.fromJson(result, DTOUlogovanNarucilac::class.java)
                if(!ulogovaniNarucilac.pronadjen)
                {
                    Toast.makeText(context,"Unijeti podaci su neispravni!",Toast.LENGTH_LONG).show()
                }
                else
                {
                    var narucilac = ulogovaniNarucilac.narucilac

                        PrefsHelper.setIdNarucioca(narucilac.ncId)
                        PrefsHelper.setKorisnikUlogovan(true)
                        PrefsHelper.setAdresa(narucilac.ncAdresa)
                        PrefsHelper.setBrTel(narucilac.ncBrojTelefona)
                        PrefsHelper.setImeNarucioca(narucilac.ncIme)
                        PrefsHelper.setUsername( ulogovaniNarucilac.korisnik.krUsername)
                        PrefsHelper.setPassword(ulogovaniNarucilac.korisnik.krPassword)

                    lin_layout_login.visibility= View.GONE
                    rvNarudzbeNarucioca.visibility = View.VISIBLE
                    Toast.makeText(context,"Uspjesna prijava",Toast.LENGTH_SHORT).show()

                    startObserving()

                }

            }

        }
        ,NetworkManager.REQUEST.ULOGUJ_NARUCIOCA,korisnikJson)

    }
    fun btnReg()
    {
        pager!!.currentItem=3
    }

}