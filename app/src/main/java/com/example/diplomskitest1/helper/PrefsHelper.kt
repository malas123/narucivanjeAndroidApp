package com.example.diplomskitest1.helper

import android.content.Context
import android.content.SharedPreferences
import com.example.diplomskitest1.firebase.MyFirebaseMessagingService

class PrefsHelper
{
    companion object {
        var context: Context? = null
        var editor: SharedPreferences.Editor? = null
        var prefs: SharedPreferences? = null

        val FIREBASE_TOKEN = "token"
        val ULOGOVAN = "korisnik_ulogovan"
        val ID_NARUCIOCA = "id_narucioca"
        val IME_NARUCIOCA = "ime_narucioca"
        val FIRST_RUN = "first_run"
        val ADRESA_DOSTAVE = "adresa_dostave"
        val BR_TEL = "br_tel"
        val E_MAIL = "e_mail"
        val USERNAME = "username"
        val PASSWORD = "pw"

        fun initPrefs(context: Context) {
            this.context = context
            prefs = context.getSharedPreferences(
                "MyPrefs", 0
            ) // 0 - for private mode
            this.editor = prefs?.edit()
            initDefaultValues()
        }



        fun initDefaultValues() {
            if (getFirstRun().equals(false)) {
               MyFirebaseMessagingService.setToken()
                setFirstRun()
            }


        }


        fun setFirstRun() {
            this.editor!!.putBoolean(FIRST_RUN, true).commit()
        }

        fun getFirstRun(): Boolean {
            return prefs!!.getBoolean(FIRST_RUN, false)
        }
        fun setFirebaseToken(token:String)
        {
            this.editor!!.putString(FIREBASE_TOKEN,token).commit()
        }
        fun setIdNarucioca(id:Int)
        {
            this.editor!!.putInt(ID_NARUCIOCA,id).commit()
        }
        fun setImeNarucioca(ime:String)
        {
            this.editor!!.putString(IME_NARUCIOCA,ime).commit()
        }
        fun setAdresa(adresa:String)
        {
            this.editor!!.putString(ADRESA_DOSTAVE,adresa).commit()
        }
        fun setBrTel(brTel:String)
        {
            this.editor!!.putString(BR_TEL,brTel).commit()
        }
        fun setEMail(eMail:String)
        {
            this.editor!!.putString(E_MAIL,eMail).commit()
        }
        fun setUsername(username:String)
        {
            this.editor!!.putString(USERNAME,username).commit()
        }
        fun setPassword(pw:String)
        {
            this.editor!!.putString(PASSWORD,pw).commit()
        }
        fun setKorisnikUlogovan(ulogovan:Boolean)
        {
            this.editor!!.putBoolean(ULOGOVAN,ulogovan).commit()
        }
        fun getFirebaseToken():String
        {
            return prefs!!.getString(FIREBASE_TOKEN," ")!!
        }
        fun getKorisnikUlogovan():Boolean
        {
          return  this.prefs!!.getBoolean(ULOGOVAN,false)
        }
        fun getIdNarucioca():Int
        {
            return this.prefs!!.getInt(ID_NARUCIOCA,0);
        }
        fun getImeNarucioca():String
        {
            return  this.prefs!!.getString(IME_NARUCIOCA,"")!!
        }
        fun getAdresa():String
        {
            return  this.prefs!!.getString(ADRESA_DOSTAVE,"")!!
        }
        fun getBrTel():String
        {
            return this.prefs!!.getString(BR_TEL,"")!!
        }
        fun getEMail():String
        {
            return this.prefs!!.getString(E_MAIL,"")!!
        }
        fun getUsername():String
        {
            return this.prefs!!.getString(USERNAME,"")!!
        }
        fun getPassword():String
        {
            return this.prefs!!.getString(PASSWORD,"")!!
        }
        fun deleteKeys()
        {
            //this.editor?.remove(ULOGOVAN)?.commit()
            this.editor?.clear()?.commit()

            initDefaultValues()
        }
    }
}