package com.example.diplomskitest1.models

class NoviNarucilac
{
    var narucilac:Narucilac
    var korisnik:Korisnik
    var editovanje=false

    constructor(narucilac: Narucilac, korisnik: Korisnik, editovanje: Boolean) {
        this.narucilac = narucilac
        this.korisnik = korisnik
        this.editovanje = editovanje
    }
}