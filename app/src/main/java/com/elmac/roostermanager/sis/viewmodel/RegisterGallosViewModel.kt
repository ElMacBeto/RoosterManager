package com.elmac.roostermanager.sis.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmac.roostermanager.data.datasource.entities.GalloEntity
import com.elmac.roostermanager.data.repository.GallosRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class RegisterGallosViewModel: ViewModel() {

    private val repository = GallosRepository()
    val gallo = MutableLiveData<GalloEntity>()
    val fragment = MutableLiveData<Boolean>()
    val db = Firebase.firestore

    fun setInitValues(id:Int){
        viewModelScope.launch {
            repository.getGalloById(id)
        }
    }

    fun validarData(gallo:GalloEntity){

        viewModelScope.launch {
            val addGallo = hashMapOf(
                "line" to gallo.line,
                "year" to gallo.year,
                "plaque" to gallo.plaque,
                "ring" to gallo.ring,
                "gender" to gallo.gender,
                "leftLeg" to gallo.leftLeg,
                "rightLeg" to gallo.rightLeg,
                "noise" to gallo.noise
            )
            //repository.add(gallo)
            db.collection("gallos")
                .add(addGallo)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    fragment.postValue(true)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }
    }


}