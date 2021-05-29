package com.example.diplomskitest1.models

class TipArtikla {
   var taId:Int
    var taNazivTipa:String
   var taKod:Int

    constructor(taId: Int, taNazivTipa: String, taKod: Int) {
        this.taId = taId
        this.taNazivTipa = taNazivTipa
        this.taKod = taKod
    }
}