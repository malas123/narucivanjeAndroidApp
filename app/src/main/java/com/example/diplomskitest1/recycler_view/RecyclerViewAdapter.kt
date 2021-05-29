package com.example.diplomskitest1.recycler_view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.fragment.app.Fragment
import com.example.diplomskitest1.R
import com.example.diplomskitest1.models.*
import com.example.diplomskitest1.ui.main.MainFragment


class RecyclerViewAdapter :RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    var listaArtikala:List<ArtikalWDetails>
    var context:Context? = null
    val mainFragment:Fragment

    constructor(
       listArtikli:List<ArtikalWDetails>,
        context: Context?,
       fragment: Fragment
    ) : super() {
       this.listaArtikala = listArtikli
        this.context = context
        this.mainFragment = fragment


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_artikal,parent,false)
        var viewHolder=ViewHolder(view)

        return  viewHolder
    }

    override fun getItemCount(): Int {
        return  listaArtikala.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvHeader.visibility = View.GONE
        holder.tvNaziv.setText(listaArtikala[position].artikal?.arNaziv)
        holder.tvOpis.setText(listaArtikala[position].artikal?.arOpis)
        stringToImage(listaArtikala[position].artikal!!.arSlika,holder.imgView)
        var spinnerItems= mutableListOf<ArtikalNaruzbe>()


         val porcije = listaArtikala[position].porcije!!.iterator()
        val cijene = listaArtikala[position].cijenePorcija!!.iterator()

        for(porcija in porcije)        {


            for (cijena in cijene)
            {
                if(porcija.pcId ==(cijena.pcId))
                {
                    spinnerItems.add(ArtikalNaruzbe(listaArtikala[position].artikal!!,porcija,cijena))
                    break
                }
            }

        }
        /*
        holder.tvHeader.visibility = View.VISIBLE
        holder.tvHeader.text = listaArtikala[position].tipArtikla!!.taNazivTipa
        */


        if(position == 0 || listaArtikala[position].tipArtikla!!.taId!= listaArtikala[position-1].tipArtikla!!.taId)
        {
            holder.tvHeader.visibility = View.VISIBLE
            holder.tvHeader.text = listaArtikala[position].tipArtikla!!.taNazivTipa



        }

        holder.spPorcije.adapter = ArrayAdapter<ArtikalNaruzbe>(context!!,android.R.layout.simple_spinner_item,spinnerItems)
       // holder.spPorcije.background =
        holder.btnDodajArtikal.setOnClickListener(View.OnClickListener { btnDodajArtikalNarudzbe(holder.spPorcije.selectedItem as ArtikalNaruzbe) })

        //holder.
        /*
        holder.parentLayout.setOnClickListener {
            Toast.makeText(context,"Kliknuto na sliku " + listaArtikala[position].artikal!!.arNaziv,Toast.LENGTH_SHORT).show()
        }

         */

    }
    fun btnDodajArtikalNarudzbe(artikal: ArtikalNaruzbe)
    {
        Toast.makeText(context,"Artikal dodat na nadruzbu: " + artikal.artikal.arNaziv + " cijena: " + artikal.cijena.cpCijena,Toast.LENGTH_SHORT).show()
       // artikal.artikal.arSlika = ""//sliku necemo slati na server, nema potrebe. Nama bi trebao samo artikal id pa bi bilo pametno samo zadrzati tu varijablu?
        (mainFragment as MainFragment).addItemToList(artikal)//dodaj artikal na listu
    }

    fun stringToImage(imgStr:String , imgView:ImageView)
    {
        var bytes = Base64.decode(imgStr,Base64.DEFAULT)
        val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        if(bmp==null)
        {
            Log.d("CREATION","RESP: KONVERZIJA SLIKE NIJE USPJELA "  )
            Toast.makeText(context,"KONVERZIJA SLIKE NIJE USPJELA " ,Toast.LENGTH_SHORT)
            return
        }
        else
        {
            Log.d("CREATION","Radi top"  )
        }

        imgView.setImageBitmap(bmp)


    }
     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          var tvNaziv :TextView
         var tvOpis:TextView
         var spPorcije:Spinner
         var parentLayout:LinearLayout
         var imgView: ImageView
         var btnDodajArtikal : ImageButton
         var tvHeader: TextView

         init {
             imgView = itemView.findViewById(R.id.img)
             tvNaziv = itemView.findViewById(R.id.tv_naziv)
             parentLayout = itemView.findViewById(R.id.parent_layout)
             tvOpis = itemView.findViewById(R.id.tv_opis)
             spPorcije = itemView.findViewById(R.id.spinner_porcije)
             btnDodajArtikal = itemView.findViewById(R.id.btn_dodaj)
             tvHeader = itemView.findViewById(R.id.tv_tip_artikla)
         }
    }

    }
