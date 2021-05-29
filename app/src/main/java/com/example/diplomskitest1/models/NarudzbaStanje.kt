package com.example.diplomskitest1.models

class NarudzbaStanje
{
    var narudzba:NarudzbaDbModel
    var brArtikala:Int
   // var narucilac:Narucilac
    var stanje:String

    constructor(narudzba: NarudzbaDbModel,brArtikala:Int, narucilac: Narucilac,stanje:String) {
        this.brArtikala = brArtikala
       // this.narucilac = narucilac
        this.narudzba = narudzba
        this.stanje= stanje


    }
}