package com.example.pertemuan14.repository

import com.example.pertemuan14.model.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface RepositoryMhs {
    //Suspend Untuk operasi berat create,update,delete
    suspend fun insertMhs(mahasiswa: Mahasiswa)

    fun getAllMhs() : Flow<List<Mahasiswa>>

    fun getMhs(nim : String) : Flow<Mahasiswa>

    suspend fun deleteMhs(mahasiswa: Mahasiswa)

    suspend fun updateMhs(mahasiswa: Mahasiswa)

}