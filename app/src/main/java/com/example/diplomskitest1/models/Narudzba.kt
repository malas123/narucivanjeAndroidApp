package com.example.diplomskitest1.models

class Narudzba
{
    var stavkeNaruzbe:MutableList<StavkaNarudzbe>
    var narucilac:Narucilac
    var ulogovan = false
    var napomena:String = ""
    var korisnik:Korisnik? = null
    var cijena= 0f

    constructor(stavkeNaruzbe: MutableList<StavkaNarudzbe>, narucilac: Narucilac,napomena:String,cijena:Float,ulogovan:Boolean) {
        this.stavkeNaruzbe = stavkeNaruzbe
        this.narucilac = narucilac
        this.napomena = napomena
        this.cijena  = cijena
        this.ulogovan = ulogovan
        // this.korisnik = korisnik

    }

}