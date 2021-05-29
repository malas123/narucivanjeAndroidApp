package com.example.diplomskitest1.ui.main

import android.content.Context
import android.net.Network
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.HandlerCompat.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2

import com.example.diplomskitest1.R
import com.example.diplomskitest1.helper.PrefsHelper
import com.example.diplomskitest1.models.Korisnik
import com.example.diplomskitest1.models.Narucilac
import com.example.diplomskitest1.models.Narudzba
import com.example.diplomskitest1.models.StavkaNarudzbe
import com.example.diplomskitest1.network.NetworkManager
import com.example.diplomskitest1.recycler_view.RecyclerAdapterNarudzba
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_korpa.*
import org.json.JSONObject
import java.lang.Exception
import androidx.core.os.HandlerCompat


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentKorpa.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentKorpa.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentKorpa() : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var pager: ViewPager2?=null

    var cijena =0.0

    constructor(pager:ViewPager2):this()
    {
        this.pager = pager
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_korpa, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       UpdateListaArtikala()
        btn_naruci.setOnClickListener(View.OnClickListener{
            naruci()
        })


    }
    fun UpdateListaArtikala()
    {
        try {


        var adapter =  RecyclerAdapterNarudzba(MainFragment.artikliNarudzbe,requireContext()!!,this)
        rvSadrzajNarudzbe.adapter = adapter//RecyclerAdapterNarudzba(MainFragment.artikliNarudzbe,context!!)
        rvSadrzajNarudzbe.layoutManager = LinearLayoutManager(requireContext()!!)
        tv_ukupna_cijena.text = "Ukupna cijena " + MainFragment.getCijena() + " KM"
            popuniPoljaNarudzbe()
            }
        catch (e:Exception)
        {
            //:P handle ovo ono?
        }
    }
    fun popuniPoljaNarudzbe()
    {
        if(PrefsHelper.getKorisnikUlogovan())
        {
            et_ime.setText(PrefsHelper.getImeNarucioca())
            et_adresa.setText( PrefsHelper.getAdresa())
            et_tel.setText(PrefsHelper.getBrTel())
        }
        btn_naruci.visibility = View.VISIBLE
    }
    fun naruci()
    {
        if(provjeriUnose())
        {
            //poruci artikal -> post narudzba...

                var stavkeNarudzbe = mutableListOf<StavkaNarudzbe>()
                var narucilac = Narucilac(et_ime.text.toString(),et_tel.text.toString(),et_adresa.text.toString(),PrefsHelper.getFirebaseToken())
                var ulogovan = PrefsHelper.getKorisnikUlogovan()
                if(ulogovan)
                {
                    narucilac.ncId = PrefsHelper.getIdNarucioca()
                    narucilac.ncEmail = PrefsHelper.getEMail()

                }

                for(artikal in MainFragment.artikliNarudzbe)
                {
                    stavkeNarudzbe.add(StavkaNarudzbe(artikal.artikal.arId,artikal.porcija.pcId))
                }

                var novaNarudzba = Narudzba(stavkeNarudzbe,narucilac,et_napomena.text.toString(),MainFragment.getCijena(),ulogovan          )
                var json = Gson().toJson(novaNarudzba)

                Log.d("CREATION" , "RESP: " + json)

                NetworkManager.getInstance().postRequest(NetworkManager.REQUEST.NARUDZBE,json)
                MainFragment.artikliNarudzbe.clear()
            btn_naruci.visibility = View.GONE
            Handler().postDelayed(
                {

                    if(pager!=null ) {
                        pager!!.currentItem = 2
                    }

                },
                1000 // value in milliseconds
            )






        }

    }
    fun provjeriUnose():Boolean
    {
        if(MainFragment.artikliNarudzbe.count()>0) {
            if (et_ime.text.isBlank()) {
                Toast.makeText(context, "Morate unijeti ime!", Toast.LENGTH_SHORT).show()
                return false
            }
            if (et_adresa.text.isBlank()) {
                Toast.makeText(context, "Morate unijeti adresu isporuke!", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            if (et_tel.text.isBlank()) {
                Toast.makeText(context, "Morate unijeti kontakt telefon!", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            return true
        }
        else
        {
            Toast.makeText(context, "Morate naruciti minimalno 1 artikal !", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
           // throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")

        }
    }
/*
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

 */

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        var korisnikUlogovan = false
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentKorpa.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentKorpa().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
