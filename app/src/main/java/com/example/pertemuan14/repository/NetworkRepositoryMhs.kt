package com.example.pertemuan14.repository

import com.example.pertemuan14.model.Mahasiswa
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NetworkRepositoryMhs (
    private val firestore: FirebaseFirestore
) : RepositoryMhs{

    override suspend fun insertMhs (mahasiswa: Mahasiswa){
        try {
            firestore.collection("Mahasiswa").add(mahasiswa).await()
        } catch (e: Exception) {
            throw Exception ("Gagal menambahkan data mahasiswa: ${e.message}")
        }
    }

    override fun getAllMhs(): Flow<List<Mahasiswa>> = callbackFlow {
        val mhsCollection = firestore.collection("Mahasiswa")
            .orderBy("nim", Query.Direction.ASCENDING)
            .addSnapshotListener {
                value, error ->
                if(value != null){
                    val mhslist = value.documents.mapNotNull {
                        //convert dari document firestore ke data class
                        it.toObject(Mahasiswa::class.java)!!
                    }
                    //fungsi untuk mengirim collection ke data class
                    trySend(mhslist)
                }
            }
        awaitClose {
            //menutup collection dari firestore
            mhsCollection.remove()
        }

    }

    override fun getMhs(nim: String): Flow<Mahasiswa> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMhs(mahasiswa: Mahasiswa) {
        TODO("Not yet implemented")
    }

    override suspend fun updateMhs(mahasiswa: Mahasiswa) {
        TODO("Not yet implemented")
    }

}