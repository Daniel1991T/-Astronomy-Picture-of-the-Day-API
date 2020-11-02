package com.compani.ilai.astronomydaypicturenasaapi.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.compani.ilai.astronomydaypicturenasaapi.data.NasaPhoto
import com.compani.ilai.astronomydaypicturenasaapi.data.NasaPhotoRepository
import com.compani.ilai.astronomydaypicturenasaapi.utils.Resource
import com.compani.ilai.astronomydaypicturenasaapi.utils.Status
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class NasaPhotoViewModel @ViewModelInject constructor(
    repository: NasaPhotoRepository,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _nasaPhoto = MutableLiveData<Resource<NasaPhoto>>()
    val nasaPhoto: LiveData<Resource<NasaPhoto>>
        get() = _nasaPhoto

    init {
        compositeDisposable.add(
            repository.getNasaPhoto()
                .doOnSubscribe { _nasaPhoto.value = Resource(Status.LOADING) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {photo -> _nasaPhoto.value = Resource(Status.SUCCESS, photo)},
                    {error -> _nasaPhoto.value = Resource(Status.ERROR, null, error.message)}
                )
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}