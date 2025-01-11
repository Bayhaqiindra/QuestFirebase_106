package com.example.pertemuan14.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan14.model.Mahasiswa
import com.example.pertemuan14.repository.RepositoryMhs
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel (
    private val repoMhs:RepositoryMhs
) : ViewModel() {

    var mhsUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set
    init {
        getMhs()
    }

    fun getMhs() {
        viewModelScope.launch {
            repoMhs.getAllMhs().onStart {
                mhsUIState = HomeUiState.Loading
            }
                .catch {
                    mhsUIState = HomeUiState.Error(e =it)
                }
                .collect {
                    mhsUIState = if(it.isEmpty()){
                        HomeUiState.Error(Exception("Belum ada data Mahasiswa"))
                    }
                    else{
                        HomeUiState.Success(it)
                    }
                }
        }
    }

}

sealed class HomeUiState {
    //Loading
    object Loading : HomeUiState()
    //Sukses
    data class Success(val data: List<Mahasiswa>) : HomeUiState()
    //Error
    data class Error(val e: Throwable) : HomeUiState()
}