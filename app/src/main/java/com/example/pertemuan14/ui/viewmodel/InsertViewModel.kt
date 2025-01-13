package com.example.pertemuan14.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan14.model.Mahasiswa
import com.example.pertemuan14.repository.RepositoryMhs
import kotlinx.coroutines.launch

class InsertViewModel (
    private val mhs: RepositoryMhs
) : ViewModel() {
    var uiEvent: InsertUiState by mutableStateOf(InsertUiState())
        private set

    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set

    fun updateState(mahasiswaEvent: MahasiswaEvent) {
        uiEvent = uiEvent.copy(
            insertUiEvent = mahasiswaEvent,
        )
    }

    fun validateFields(): Boolean {
        val event = uiEvent.insertUiEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh Kosong",
            gender = if (event.gender.isNotEmpty()) null else "Jenis Kelamin tidak boleh Kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh Kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "Kelas tidak boleh Kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh Kosong",
            dosenpembimbing1 = if (event.dosenpembimbing1.isNotEmpty()) null else "Dosen Pembimbing 1 tidak boleh Kosong",
            dosenpembimbing2 = if (event.dosenpembimbing2.isNotEmpty()) null else "Dosen Pembimbing 2 tidak boleh Kosong",
            judulskripsi = if (event.judulskripsi.isNotEmpty()) null else "Judul Skripsi tidak boleh Kosong",
        )

        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertMhs() {
        if (validateFields()) {
            viewModelScope.launch {
                uiState = FormState.Loading
                try {
                    mhs.insertMhs(uiEvent.insertUiEvent.toMhsModel())
                    uiState = FormState.Success("Data berhasil disimpan")
                } catch (e: Exception) {
                    uiState = FormState.Error("Data gagal disimpan")
                }
            }
        } else {
            uiState = FormState.Error("Data tidak valid")
        }
    }

    fun resetForm(){
        uiEvent = InsertUiState()
        uiState = FormState.Idle
    }

    fun resetSnackBarMessage(){
        uiState = FormState.Idle
    }
}
sealed class FormState {
    object Idle : FormState()
    object Loading : FormState()
    data class Success(val message: String) : FormState()
    data class Error(val message: String) : FormState()
    }

data class InsertUiState(
    val insertUiEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    )

data class FormErrorState(
    val nim: String? = null,
    val nama: String? = null,
    val gender: String? = null,
    val alamat: String? = null,
    val kelas: String? = null,
    val angkatan: String? = null,
    val dosenpembimbing1: String? = null,
    val dosenpembimbing2: String? = null,
    val judulskripsi: String? = null,
) {
    fun isValid(): Boolean {
        return nim == null && nama == null && gender == null &&
                alamat == null && kelas == null && angkatan == null
                && dosenpembimbing1 == null && dosenpembimbing2 == null
                && judulskripsi == null
    }
}

data class MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    val alamat: String = "",
    val gender: String = "",
    val kelas: String = "",
    val angkatan: String = "",
    val dosenpembimbing1: String = "",
    val dosenpembimbing2: String = "",
    val judulskripsi: String = "",
)

fun MahasiswaEvent.toMhsModel(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    alamat = alamat,
    gender = gender,
    kelas = kelas,
    angkatan = angkatan,
    dosenpembimbing1 = dosenpembimbing1,
    dosenpembimbing2 = dosenpembimbing2,
    judulskripsi = judulskripsi,
)