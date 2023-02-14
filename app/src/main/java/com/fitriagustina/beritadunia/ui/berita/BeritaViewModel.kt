package com.fitriagustina.beritadunia.ui.berita

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.fitriagustina.beritadunia.data.network.model.BeritaModel
import com.fitriagustina.beritadunia.data.repo.BeritaRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.Response

//View Model untuk berita dengan implementasi BeritaRepo
class BeritaViewModel(private val beritaRepo: BeritaRepo
) : ViewModel() {
    val loading = MutableLiveData<Boolean>() //untuk menyimpan data status loading
    val errorMessage = MutableLiveData<String>() //untuk menyimpan data error
    var beritaResponse = MutableLiveData<Response<BeritaModel>>() //menyimpan data berita

    //request data berita ke API
    fun getBerita(){
        loading.value = true //set loading true
        viewModelScope.launch { //menjalan kan couroutine scope
            val response = beritaRepo.getBerita() //request berita kemudian mengambil data feedback dari API

            //jika request berhasil
            if (response.isSuccessful){
                beritaResponse.postValue(response) //menyimpan data berita ke variable beritaResponse
                loading.value = false //set loading false
            } else { //jika request gagal
                errorMessage.value = response.message() //menyimpan error message
                loading.value = false //set loading false
            }
        }
    }
}