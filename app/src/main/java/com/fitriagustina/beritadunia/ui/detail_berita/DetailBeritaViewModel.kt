package com.fitriagustina.beritadunia.ui.detail_berita

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.fitriagustina.beritadunia.data.room.entity.BeritaEntity
import com.fitriagustina.beritadunia.data.room.repo.RoomRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.Response

//View Model untuk detail berita dengan implementasi RoomRepo
class DetailBeritaViewModel(private val roomRepo: RoomRepo
) : ViewModel() {

    private val disposable = CompositeDisposable()

    //variable untuk menyimpan status bookmark pada berita
    var isBookmarked = false

    //fungsi untuk menambah data bookmark ke room database dengan menggunakan rxJava2
    fun addBookmark(beritaEntity: BeritaEntity, doSubscribe: () -> Unit, doError:(e: Throwable) -> Unit){
        launch {
            roomRepo.addBookmark(beritaEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    doSubscribe.invoke()
                }){
                    doError.invoke(it)
                }
        }
    }

    //mengambil data bookmark di db untuk mengecek apakah berita ini ter bookmark atau tidak
    fun getBookmark(title: String, doSubscribe: (data: BeritaEntity) -> Unit, doError:(e: Throwable) -> Unit){
        launch {
            roomRepo.getBookmark(title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isBookmarked = true
                    doSubscribe.invoke(it)
                }){
                    isBookmarked = false
                    doError.invoke(it)
                }
        }
    }

    //menghapus data bookmark di db
    fun deleteBookmark(title: String, doSubscribe: () -> Unit, doError:(e: Throwable) -> Unit){
        launch {
            roomRepo.deleteBookmark(title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    doSubscribe.invoke()
                }){
                    doError.invoke(it)
                }
        }
    }

    fun launch(job: () -> Disposable) {
        disposable.add(job())
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}