package com.example.diplomskitest1.ui.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.diplomskitest1.R
import com.example.diplomskitest1.helper.PrefsHelper
import com.example.diplomskitest1.models.Korisnik
import com.example.diplomskitest1.models.Narucilac
import com.example.diplomskitest1.models.NoviNarucilac
import com.example.diplomskitest1.models.VolleyCallback
import com.example.diplomskitest1.network.NetworkManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_korpa.*
import kotlinx.android.synthetic.main.fragment_nalog.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentNalog.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentNalog.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentNalog : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private  var isCreated =false
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
        return inflater.inflate(R.layout.fragment_nalog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        refreshState()
      isCreated = true
        btn_novi_nalog.setOnClickListener(View.OnClickListener { sendData() })
        btn_logout.setOnClickListener(View.OnClickListener { btnLogout() })
    }
    fun refreshState()
    {
        if(isCreated) {
            if (PrefsHelper.getKorisnikUlogovan()) {
                populateFields()
                btn_novi_nalog.setText("Izmjeni podatke")
                btn_logout.visibility = View.VISIBLE

            } else {
                depopulateFields()
                btn_novi_nalog.setText("Registracija")
                btn_logout.visibility = View.GONE
            }
        }
    }
    fun sendData()
    {
        if(checkData())
        {

                val email = et_nalog_email.text.toString()
                val adresa = et_nalog_adresa.text.toString()
                val brTel = et_nalog_br_tel.text.toString()
                val username = et_nalog_username.text.toString()
                val pw = et_nalog_pw1.text.toString()
                var ime = et_nalog_ime.text.toString()
                var narucilac = Narucilac(
                    ime,
                    email,
                    adresa,
                    brTel,
                    0f,
                    PrefsHelper.getFirebaseToken()
                )

            var editovanje = PrefsHelper.getKorisnikUlogovan()
            var noviNarucilac = NoviNarucilac(
                narucilac,
                Korisnik(username, pw),
                PrefsHelper.getKorisnikUlogovan()
            )
            if(editovanje)
            {
                noviNarucilac.narucilac.ncId = PrefsHelper.getIdNarucioca()
                noviNarucilac.narucilac.ncToken = PrefsHelper.getFirebaseToken()
            }

                var gson = Gson()
                var json = gson.toJson(noviNarucilac)
                Log.d("CREATION" , "RESP: json naloga " + json)
                //NetworkManager.getInstance().postRequest(NetworkManager.REQUEST.NARUCILAC, json)
                NetworkManager.getInstance().postRequestWithCallback(object :VolleyCallback{
                    override fun onSuccess(result: String) {
                        if(result.toString().contains(":")) {
                            var resp = result.split(":")
                            var msg = resp[0]
                            var ncId = resp[1].toInt()
                            Log.d("CREATION", "RESP: ID narucioca je " + resp[1])

                            PrefsHelper.setIdNarucioca(ncId)
                            PrefsHelper.setKorisnikUlogovan(true)
                            PrefsHelper.setAdresa(adresa)
                            PrefsHelper.setBrTel(brTel)
                            PrefsHelper.setImeNarucioca(ime)
                            PrefsHelper.setUsername(username)
                            PrefsHelper.setPassword(pw)
                            PrefsHelper.setEMail(email)
                            populateFields()

                        }
                            else if(result.contains("!"))
                        {

                            Toast.makeText(context, result, Toast.LENGTH_SHORT).show()


                            PrefsHelper.setAdresa(adresa)
                            PrefsHelper.setBrTel(brTel)
                            PrefsHelper.setImeNarucioca(ime)
                            PrefsHelper.setUsername(username)
                            PrefsHelper.setPassword(pw)
                            PrefsHelper.setEMail(email)
                            populateFields()
                        }
                            else {
                            Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                            Log.d("CREATION", "RESP: not ")
                        }
                    }

                },NetworkManager.REQUEST.NARUCILAC,json)




        }


    }
    fun checkData():Boolean
    {
        if(et_nalog_ime.text.isNullOrBlank())
        {
            Toast.makeText(context,"Ime nije uneseno", Toast.LENGTH_LONG).show()
            return false
        }
        if(et_nalog_adresa.text.isNullOrBlank())
        {
            Toast.makeText(context,"Adresa dostave nije unijeta", Toast.LENGTH_LONG).show()
            return false
        }
        if(et_nalog_br_tel.text.isNullOrBlank())
        {
            Toast.makeText(context,"Kontakt telefon nije unesen", Toast.LENGTH_LONG).show()
            return false
        }
        if(et_nalog_username.text.isNullOrBlank())
        {
            Toast.makeText(context,"Korisnicko ime nije uneseno", Toast.LENGTH_LONG).show()
            return false;
        }
        if(et_nalog_pw1.text.isNullOrBlank())
        {
            Toast.makeText(context,"Password nije unesen", Toast.LENGTH_LONG).show()
            return false
        }
        if(et_nalog_pw1.text.toString() != et_nalog_pw2.text.toString())
        {
            Toast.makeText(context,"Passwordi se ne podudaraju!", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
    fun populateFields()
    {
         et_nalog_email.setText(PrefsHelper.getEMail())
        et_nalog_adresa.setText(PrefsHelper.getAdresa())
        et_nalog_br_tel.setText(PrefsHelper.getBrTel())
         et_nalog_username.setText(PrefsHelper.getUsername())
        et_nalog_pw1.setText(PrefsHelper.getPassword())
        et_nalog_ime.setText(PrefsHelper.getImeNarucioca())
    }
    fun depopulateFields()//:)
    {
        et_nalog_email.setText("")
        et_nalog_adresa.setText("")
        et_nalog_br_tel.setText("")
        et_nalog_username.setText("")
        et_nalog_pw1.setText("")
        et_nalog_ime.setText("")
    }
    fun btnLogout()
    {
        PrefsHelper.deleteKeys()
        refreshState()
    }
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentNalog.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentNalog().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
