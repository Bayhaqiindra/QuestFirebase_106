package com.example.pertemuan14.model

data class Mahasiswa(
    val nim:String,
    val nama:String,
    val alamat:String,
    val gender:String,
    val kelas:String,
    val angkatan:String,
    val dosenpembimbing1:String,
    val dosenpembimbing2: String,
    val judulskripsi:String,
) {
    constructor() : this("","","","","","","","","")
}

