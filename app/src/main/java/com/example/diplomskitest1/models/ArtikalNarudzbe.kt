package com.example.diplomskitest1.models

class ArtikalNaruzbe {
    var artikal: Artikal
    var porcija: Porcija
    var cijena: CijenaPorcije

    constructor(artikal: Artikal, porcija: Porcija, cijena: CijenaPorcije) {
        this.artikal = artikal
        this.porcija = porcija
        this.cijena = cijena
    }

    override fun toString(): String {
        return porcija.pcNaziv + ": " + cijena.cpCijena + " KM"
    }
}