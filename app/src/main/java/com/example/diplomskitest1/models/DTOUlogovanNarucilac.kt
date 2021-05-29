package com.example.diplomskitest1.models

class DTOUlogovanNarucilac
{
    var pronadjen:Boolean
    var narucilac:Narucilac
    var korisnik:Korisnik

    constructor(pronadjen: Boolean, narucilac: Narucilac,korisnik: Korisnik) {
        this.pronadjen = pronadjen
        this.narucilac = narucilac
        this.korisnik = korisnik
    }
}