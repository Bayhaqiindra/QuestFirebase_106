package com.example.pertemuan14.ui.viewmodel

import com.example.pertemuan14.model.Mahasiswa

fun MahasiswaEvent.toMhsModel() : Mahasiswa = Mahasiswa (
    nim = nim,
    nama = nama,
    jenisKelamin = jenisKelamin,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan
)