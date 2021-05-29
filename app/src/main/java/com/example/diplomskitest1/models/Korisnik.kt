package com.example.diplomskitest1.models

class Korisnik {
    var  krId =0
    var  ulId=0
    var krUsername =""
    var krPassword =""

    constructor(krUsername: String, krPassword: String) {
        this.krUsername = krUsername
        this.krPassword = krPassword
    }
    constructor()
    {

    }
}