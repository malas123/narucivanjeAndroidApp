package com.example.diplomskitest1.recycler_view

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.Visibility
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomskitest1.R
import com.example.diplomskitest1.models.ArtikalNaruzbe
import com.example.diplomskitest1.ui.main.FragmentKorpa
import kotlinx.android.synthetic.main.layout_recycler_sadrzaj_narudzbe.view.*
import org.w3c.dom.Text

class RecyclerAdapterNarudzba:    RecyclerView.Adapter<RecyclerAdapterNarudzba.ViewHolderArtikalNarudzbe>
{
    var artikliNarudzbe :MutableList<ArtikalNaruzbe>
    var context:Context
    var korpaFragment: FragmentKorpa

    constructor(artikliNarudzbe: MutableList<ArtikalNaruzbe>, context: Context,fragmentKorpa: FragmentKorpa) : super() {
        this.artikliNarudzbe = artikliNarudzbe
        this.context = context
        korpaFragment = fragmentKorpa
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderArtikalNarudzbe {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_sadrzaj_narudzbe,parent,false)
        var viewHolder= ViewHolderArtikalNarudzbe(view)
        return  viewHolder
    }

    override fun getItemCount(): Int {
        return artikliNarudzbe.count()
    }

    override fun onBindViewHolder(holder: ViewHolderArtikalNarudzbe, position: Int) {
        holder.nazivPorcije.setText( artikliNarudzbe[position].porcija.pcNaziv + " " +  artikliNarudzbe[position].artikal.arNaziv)
        holder.cijenaPorcije.setText( "Cijena: "+ artikliNarudzbe[position].cijena.cpCijena)
        stringToImage(artikliNarudzbe[position].artikal!!.arSlika,holder.img)

        holder.btnUkloni.setOnClickListener(View.OnClickListener { artikliNarudzbe.removeAt(position)
        holder.itemView.visibility = View.INVISIBLE
        korpaFragment.UpdateListaArtikala()
        })

    }



    fun stringToImage(imgStr:String , imgView:ImageView) {
        var bytes = Base64.decode(imgStr, Base64.DEFAULT)
        val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        if (bmp == null) {
            Log.d("CREATION", "RESP: KONVERZIJA SLIKE NIJE USPJELA ")
            Toast.makeText(context, "KONVERZIJA SLIKE NIJE USPJELA ", Toast.LENGTH_SHORT)
            return
        } else {
            Log.d("CREATION", "Radi top")
        }

        imgView.setImageBitmap(bmp)
    }
        class ViewHolderArtikalNarudzbe(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        var nazivPorcije:TextView
        var cijenaPorcije:TextView
        var img:ImageView
        var btnUkloni:ImageButton

        init
        {
            this.nazivPorcije = itemView.tv_naziv_jela
            this.cijenaPorcije=itemView.tv_cijena
            this.img=itemView.narudzba_img
            this.btnUkloni=itemView.btn_ukloni
        }
    }
}
