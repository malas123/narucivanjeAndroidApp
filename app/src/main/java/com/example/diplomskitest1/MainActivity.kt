package com.example.diplomskitest1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.diplomskitest1.firebase.MyFirebaseMessagingService
import com.example.diplomskitest1.helper.PrefsHelper
import com.example.diplomskitest1.pager_adapter.ViewPager2Adapter
import com.example.diplomskitest1.ui.main.FragmentKorpa
import com.example.diplomskitest1.ui.main.FragmentNalog
import com.example.diplomskitest1.ui.main.FragmentNarudzbe
import com.example.diplomskitest1.ui.main.MainFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.layout_no_internet.*
import kotlinx.android.synthetic.main.layout_pager_tab.*


class MainActivity : AppCompatActivity() {

    var fragmentPages:MutableList<Fragment>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }*/


        MyFirebaseMessagingService.initService {showMessage() }
        createNotificationChannel()
        PrefsHelper.initPrefs(applicationContext)


       setContent()

    }

    private fun setContent()
    {
        //Toast.makeText(applicationContext,"Stanje " + isNetworkConnected(),Toast.LENGTH_LONG).show()
        if(isNetworkConnected()==false)
        {
            setContentView(R.layout.layout_no_internet)
            btn_refresh_konekciju.setOnClickListener {
                Toast.makeText(applicationContext, "Konekcija nije ostvarena...", Toast.LENGTH_SHORT)
                setContent()
            }

        }
        else {
            setContentView(R.layout.layout_pager_tab)
            fragmentPages = mutableListOf()
            fragmentPages!!.add(MainFragment(viewPager))
            fragmentPages!!.add(FragmentKorpa(viewPager))
            fragmentPages!!.add(FragmentNarudzbe(viewPager))
            fragmentPages!!.add(FragmentNalog());




            viewPager.adapter =
                ViewPager2Adapter(getSupportFragmentManager(), lifecycle, fragmentPages!!)
            viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {

                    if (position == 0) {
                        (fragmentPages!![0] as MainFragment).updateCijena()
                    }
                    if (position == 1) {

                        (fragmentPages!![1] as FragmentKorpa).UpdateListaArtikala()
                        //(fragmentPages!![1] as FragmentKorpa).()
                    }
                    if (position == 2) {
                        (fragmentPages!![position] as FragmentNarudzbe).refreshState()
                    }
                    if (position == 3) {
                        (fragmentPages!![position] as FragmentNalog).refreshState()
                    }
                }

            })
            var tabLayout = tabs
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                var header = ""
                if (position == 0) {
                    header = "Artikli"

                } else if (position == 1) {
                    header = "Naruci"
                } else if (position == 2) {
                    header = "Moje Narudzbe"
                } else if (position == 3) {
                    header = "Nalog"
                }
                tab.text = header
            }.attach()
        }
    }
    private fun isNetworkConnected(): Boolean {
        val cm =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }
    interface NotificationCallback{

        fun getData(data:String)
        fun  showData(data:String,context: Context)
    }
    fun showMessage()
    {
        Toast.makeText(applicationContext,"Radi",Toast.LENGTH_LONG).show()
    }

    fun createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_desc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("narudzba", name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

}
