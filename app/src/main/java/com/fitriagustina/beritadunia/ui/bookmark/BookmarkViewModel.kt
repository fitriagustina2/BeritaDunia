package com.fitriagustina.beritadunia.ui.bookmark

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

//View Model untuk bookmark dengan implementasi RoomRepo
class BookmarkViewModel(private val roomRepo: RoomRepo
) : ViewModel() {

    private val disposable = CompositeDisposable()

    //fungsi untuk mengambil data bookmark dari room database dengan menggunakan rxJava2
    fun getBookmarks(doSubscribe: (data: List<BeritaEntity>) -> Unit, doError:(e: Throwable) -> Unit){
        launch {
            roomRepo.getBookmarks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    doSubscribe.invoke(it)
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