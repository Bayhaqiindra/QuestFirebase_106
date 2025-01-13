package com.example.pertemuan14.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan14.model.Mahasiswa
import com.example.pertemuan14.repository.RepositoryMhs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    //Loading
    object Loading : DetailUiState()
    //Sukses
    data class Success(val data: List<Mahasiswa>) : DetailUiState()
    //Error
    data class Error(val e: Throwable) : DetailUiState()
}

class DetailViewModel (
    private val mhs: RepositoryMhs
) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

}