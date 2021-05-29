package com.example.diplomskitest1.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request

import com.android.volley.VolleyError
import org.json.JSONObject
import retrofit2.http.POST

import com.android.volley.toolbox.Volley
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.diplomskitest1.models.Artikal
import com.example.diplomskitest1.ui.main.MainViewModel
import com.google.gson.JsonObject
import org.json.JSONException
import java.security.cert.X509Certificate
import javax.net.ssl.*
import com.android.volley.VolleyLog
import com.android.volley.AuthFailureError
import com.example.diplomskitest1.helper.PrefsHelper
import com.example.diplomskitest1.models.VolleyCallback
import java.io.UnsupportedEncodingException


class NetworkManager private constructor(context: Context) {

    //for Volley API
    var requestQueue: RequestQueue
    enum class REQUEST {ARTIKLI,NARUDZBE,KORISNICI,TIP_ARTIKLA,NARUCILAC,ULOGUJ_NARUCIOCA}
    val baseUrl ="http://104.248.247.156:32560/api/"//"http://diplv3.westeurope.azurecontainer.io/api/" ////u dockeru je 44312!"https://192.168.1.5:44312/api/"
    val urlRequestHashMap:HashMap<REQUEST,String> = hashMapOf(REQUEST.ARTIKLI to "Artikals/Android/",REQUEST.NARUDZBE to "Narudzba/Android/test/",REQUEST.KORISNICI to "Korisnik/",/*za test*/REQUEST.TIP_ARTIKLA to "TipArtikla" ,
    REQUEST.NARUCILAC to "Narucilacs/Android",REQUEST.ULOGUJ_NARUCIOCA to "Narucilacs/Android/ulogujKorisnika")
    var context:Context
    init {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext())
        this.context =context

        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array< java.security.cert.X509Certificate >  {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}
        })

        // Install the all-trusting trust manager
        val sc = SSLContext.getInstance("SSL") // Add in try catch block if you get error.
        sc.init(
            null,
            trustAllCerts,
            java.security.SecureRandom()
        ) // Add in try catch block if you get error.
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)

        // Create all-trusting host name verifier
        val allHostsValid = object : HostnameVerifier {
            override fun verify(hostname: String, session: SSLSession): Boolean {
                return true
            }
        }

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid)

    }
    fun getRequest(callback: VolleyCallback, requestType:REQUEST)
    {
        val stringRequest = StringRequest(
            Request.Method.GET, baseUrl +urlRequestHashMap[requestType] ,
            Response.Listener<String> { response ->
                Log.d("CREATION","RESPONSE: Doslo je do odgovora" +    response.toString());
                callback.onSuccess(response)

            },
            Response.ErrorListener {    })
        requestQueue.add(stringRequest)
    }
    fun getRequest( callback: VolleyCallback, requestType:REQUEST,id:String)
    {
        val stringRequest = StringRequest(
            Request.Method.GET, baseUrl +urlRequestHashMap[requestType] + id,
            Response.Listener<String> { response ->
                Log.d("CREATION","RESPONSE: Doslo je do odgovora" +    response.toString());
                callback.onSuccess(response)

            },
            Response.ErrorListener {    })
        requestQueue.add(stringRequest)
    }
     fun postRequest(/*callback: MainViewModel.VolleyCallback,*/requestType: REQUEST,json:String)
    {
        val stringRequest: StringRequest = object : StringRequest( Method.POST, baseUrl+urlRequestHashMap[requestType],
            Response.Listener { response ->
               var msg = response
                if (requestType == REQUEST.NARUCILAC)
                {
                    if(response.toString().contains(":"))
                    {
                        var resp = response.split(":")
                        msg = resp[0]
                        var ncId = resp[1].toInt()
                        Log.d("CREATION","RESP: ID narucioca je " +resp[1])
                        PrefsHelper.setIdNarucioca(ncId)
                        PrefsHelper.setKorisnikUlogovan(true)
                    }
                }
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, error.toString() +baseUrl+urlRequestHashMap[requestType] + "\nJson " + json, Toast.LENGTH_LONG).show()
                Toast.makeText(context,"NARUDZBA NEUSPJESNA!\nProvjeri internet konekciju",Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                //Change with your post params
                params["tipArtikla"] = json

                return params
            }

            override fun getHeaders(): MutableMap<String, String> {
                var headers = HashMap<String,String>()
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers
            }

            override fun getParamsEncoding(): String {
                return "application/json"
            }


           override fun getBody(): ByteArray? {
                try {
                    return if (json == null) null else json.toByteArray(Charsets.UTF_8)
                } catch (uee: UnsupportedEncodingException) {
                    VolleyLog.wtf(
                        "Unsupported Encoding while trying to get the bytes of %s using %s",
                        json,
                        "utf-8"
                    )
                    return null
                }

            }
        }

        requestQueue.add(stringRequest)
    }

    fun postRequestWithCallback(callback: VolleyCallback,requestType: REQUEST,json:String)
    {
        val stringRequest: StringRequest = object : StringRequest( Method.POST, baseUrl+urlRequestHashMap[requestType],
            Response.Listener { response ->
                var msg = response
                callback.onSuccess(response)

            },
            Response.ErrorListener { error ->
                Toast.makeText(context, error.toString() +baseUrl+urlRequestHashMap[requestType] + "\nJson " + json, Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                //Change with your post params
                params["tipArtikla"] = json

                return params
            }

            override fun getHeaders(): MutableMap<String, String> {
                var headers = HashMap<String,String>()
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers
            }

            override fun getParamsEncoding(): String {
                return "application/json"
            }


            override fun getBody(): ByteArray? {
                try {
                    return if (json == null) null else json.toByteArray(Charsets.UTF_8)
                } catch (uee: UnsupportedEncodingException) {
                    VolleyLog.wtf(
                        "Unsupported Encoding while trying to get the bytes of %s using %s",
                        json,
                        "utf-8"
                    )
                    return null
                }

            }
        }

        requestQueue.add(stringRequest)
    }
    /*
    fun postRequestV2(requestType: REQUEST,json:JSONObject)
    {
        val stringRequest: JsonObjectRequest = object : JsonObjectRequest( Method.POST, baseUrl+urlRequestHashMap[requestType],json,
            Response.Listener { response ->
                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()
                  try {
                       val jsonObject = JSONObject(response.toString())
                       //Parse your api responce here

                       /*val intent = Intent(this, MainActivity::class.java)
                       startActivity(intent)*/
                   } catch (e: JSONException) {
                       e.printStackTrace()
                   }
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, error.toString() +baseUrl+urlRequestHashMap[requestType] + "\nJson " + json, Toast.LENGTH_LONG).show()
                Log.d("CREATION","RESP: json : " + json)
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                //Change with your post params
               // params["nova_narudzba"] = json

                return params
            }

            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }

            override fun getParamsEncoding(): String {
                return "application/x-www-form-urlencoded";
            }

        }
        requestQueue.add(stringRequest)

    }*/


    companion object {
        private val TAG = "NetworkManager"
        private var instance: NetworkManager? = null

        private val prefixURL = "http://some/url/prefix/"

        @Synchronized
        fun getInstance(context: Context): NetworkManager {
            if (null == instance)
                instance = NetworkManager(context)
            return instance!!
        }

        //this is so you don't need to pass context each time
        @Synchronized
        fun getInstance(): NetworkManager {
            checkNotNull(instance) { NetworkManager::class.java.simpleName + " is not initialized, call getInstance(...) first" }
            return instance!!
        }
    }
}