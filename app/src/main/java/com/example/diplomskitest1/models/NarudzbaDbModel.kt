package com.example.diplomskitest1.models

import java.time.OffsetDateTime

class NarudzbaDbModel
{
    var nrId: Int  
    var  ncId :Int
    var  nrVrijeme :String
    var  nrNapomene:String
    var nrCijena:Float


    constructor(nrId: Int, ncId: Int, nrVrijeme: String, nrNapomene: String, nrCijena: Float) {
        this.nrId = nrId
        this.ncId = ncId
        this.nrVrijeme = nrVrijeme
        this.nrNapomene = nrNapomene
        this.nrCijena = nrCijena
    }



}