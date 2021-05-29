package com.example.diplomskitest1.recycler_view

import android.graphics.Color
import android.graphics.Color.BLACK
import android.os.Build
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomskitest1.R
import com.example.diplomskitest1.models.ArtikalNaruzbe
import com.example.diplomskitest1.models.NarudzbaStanje
import kotlinx.android.synthetic.main.recycler_item_narudzba_narucioca.view.*
import org.w3c.dom.Text
import java.time.format.DateTimeFormatter

class RecyclerViewNarudzbeNarucioca:RecyclerView.Adapter<RecyclerViewNarudzbeNarucioca.DetaljiNarudzbeViewHolder> {

    var narudzbeNarucioca :MutableList<NarudzbaStanje>

    constructor(narudzbeNarucioca: MutableList<NarudzbaStanje>) : super() {
        this.narudzbeNarucioca = narudzbeNarucioca
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetaljiNarudzbeViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_narudzba_narucioca,parent,false)
        var viewHolder= RecyclerViewNarudzbeNarucioca.DetaljiNarudzbeViewHolder(view)
        return  viewHolder
    }

    override fun getItemCount(): Int {
        return narudzbeNarucioca.count()
    }

    override fun onBindViewHolder(holder: DetaljiNarudzbeViewHolder, position: Int) {


        var datumVrijeme =  narudzbeNarucioca[position].narudzba.nrVrijeme
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //yyyy-mm-ddThh:mm

            /*
            var datum = datumVrijeme.dayOfMonth.toString()  + "." + datumVrijeme.month.toString() + "." + datumVrijeme.year.toString()
            holder.tvDatum.text = datum
            var vrijeme = datumVrijeme.hour.toString() + ":" + datumVrijeme.minute.toString()
            holder.tvVrijeme.text = vrijeme*/
       // }
        var offset = datumVrijeme.split("T")
        var datum = offset[0]
        var offsetVrijeme = offset[1].split("+")
        var vrijeme = offsetVrijeme[0]
        var satMinSek = vrijeme.split(":")
        var sat = satMinSek[0].toInt() + 2
        var vrijemeZaPrikazati:String = sat.toString() + ":" + satMinSek[1] + ":" + satMinSek[2]
        holder.tvDatum.text = datum
        holder.tvVrijeme.text = vrijemeZaPrikazati
        holder.tvNapomena.text = narudzbeNarucioca[position].narudzba.nrNapomene
       holder.tvBrArtikala.text =  narudzbeNarucioca[position].brArtikala.toString()
        holder.tvCijena.text =  narudzbeNarucioca[position].narudzba.nrCijena.toString() + " KM"


        holder.tvStatus.text = getStanjePoruka( narudzbeNarucioca[position].stanje,holder.tvStatus)

    }
    fun getStanjePoruka(stanje:String,tv:TextView):String
    {
        if(stanje=="0")
        {
            tv.setTextColor(Color.RED)
            return "Naruzba primljena"
        }
        else if(stanje == "1")
        {
            tv.setTextColor(Color.parseColor("#C55F06"))
            return "U pripremi"
        }
        else
        {
            tv.setTextColor(Color.GREEN)
            return "Narudzba zavrsena"
        }
    }

    class DetaljiNarudzbeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDatum:TextView
        var tvVrijeme:TextView
        var tvBrArtikala:TextView
        var tvNapomena:TextView
        var tvCijena:TextView
        var tvStatus:TextView

        init
        {
            tvDatum = itemView.tv_detalji_datum
            tvVrijeme = itemView.tv_detalji_vrijeme
            tvBrArtikala = itemView.tv_detalji_br_artikala
            tvNapomena = itemView.tv_detalji_napomena
            tvCijena = itemView.tv_detalji_cijena
            tvStatus = itemView.tv_status


        }
    }
}