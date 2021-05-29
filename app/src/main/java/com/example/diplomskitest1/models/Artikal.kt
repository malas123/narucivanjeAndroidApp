package com.example.diplomskitest1.models

public  class Artikal
{
     var arId :Int  =0
     var taId:Int = 0
      var  arNaziv:String=""
     var arCijena :Float = 0f
     var arOpis:String = ""
     var arSlika :String = ""
     var arAktivan:String=""


    public constructor()
    {

    }


    constructor(
        arId: Int,
        taId: Int,
        arNaziv: String,
        arCijena: Float,
        arOpis: String,
        arSlika: String,
        arAktivan: String
    ) {
        this.arId = arId
        this.taId = taId
        this.arNaziv = arNaziv
        this.arCijena = arCijena
        this.arOpis = arOpis
        this.arSlika = arSlika
        this.arAktivan = arAktivan
    }

}