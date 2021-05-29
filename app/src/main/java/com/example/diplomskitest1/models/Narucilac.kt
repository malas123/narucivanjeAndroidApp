package com.example.diplomskitest1.models

 class Narucilac {
    

     var ncId:Int=0
    var krId:Int?=0
    var tnId:Int=0
    var  ncIme:String=""
    var ncEmail:String=""
    var ncAdresa:String=""
    var  ncBrojTelefona:String=""
    var ncPotrosenoNovca:Float?=0f
     var ncToken:String= ""

     constructor(
         ncId: Int,
         krId: Int?,
         tnId: Int,
         ncIme: String,
         ncEmail: String,
         ncAdresa: String,
         ncBrojTelefona: String,
         ncPotrosenoNovca: Float?,
         ncToken:String
     ) {
         this.ncId = ncId
         this.krId = krId
         this.tnId = tnId
         this.ncIme = ncIme
         this.ncEmail = ncEmail
         this.ncAdresa = ncAdresa
         this.ncBrojTelefona = ncBrojTelefona
         this.ncPotrosenoNovca = ncPotrosenoNovca
         this.ncToken = ncToken
     }
     constructor(ime:String,brTel:String,adresa:String,token:String)//gost
     {
         this.ncIme = ime
         this.ncBrojTelefona = brTel
         this.ncAdresa = adresa
         this.ncToken = token

     }
     constructor(
         ncIme: String,
         ncEmail: String,
         ncAdresa: String,
         ncBrojTelefona: String,
         ncPotrosenoNovca: Float?,
         ncToken:String
     ) {

         this.ncIme = ncIme
         this.ncEmail = ncEmail
         this.ncAdresa = ncAdresa
         this.ncBrojTelefona = ncBrojTelefona
         this.ncPotrosenoNovca = ncPotrosenoNovca
         this.ncToken = ncToken
     }
 }