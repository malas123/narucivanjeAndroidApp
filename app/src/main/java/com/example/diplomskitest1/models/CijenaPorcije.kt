package com.example.diplomskitest1.models

class CijenaPorcije
{
 var arId :Int
    var pcId:Int
    var cpCijena :Float

    constructor(arId: Int, PcId: Int, CpCijena: Float) {
        this.arId = arId
        this.pcId = PcId
        this.cpCijena = CpCijena
    }
}