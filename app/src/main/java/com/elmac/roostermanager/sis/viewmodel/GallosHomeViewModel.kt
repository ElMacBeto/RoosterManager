package com.elmac.roostermanager.sis.viewmodel

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmac.roostermanager.data.datasource.entities.GalloEntity
import com.elmac.roostermanager.data.repository.GallosRepository
import com.elmac.roostermanager.sis.ui.view.activities.RegisterGallosActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class GallosHomeViewModel: ViewModel() {
    lateinit var repository:GallosRepository

    val db = Firebase.firestore
    val gallosList = MutableLiveData<MutableList<GalloEntity>>()


    fun getAllGallos(){
        viewModelScope.launch {
            repository = GallosRepository()
            val currentGallosList = repository.getAll()
            gallosList.postValue(currentGallosList)
        }
    }

    fun addNewGallo(gallo:GalloEntity){
        viewModelScope.launch {
            repository = GallosRepository()
            repository.add(gallo)
            getAllGallos()
        }
    }

    fun deleteGallo(gallo:GalloEntity){
        viewModelScope.launch {
            repository = GallosRepository()
            repository.delete(gallo)
        }
    }

    fun changeToRegisterActivity(context:Context){
        val intent = Intent(context, RegisterGallosActivity::class.java)
        context.startActivity(intent)
    }

    fun loadGallosFirebase() {
        val newGallos = mutableListOf<GalloEntity>()
        db.collection("gallos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    println("año: " + document.get("year") as String)
                    val newGallo = GalloEntity(0, "giro", document.get("year") as String,
                        document.get("plaque") as String, document.get("ring") as String,
                        document.get("gender") as String, document.get("leftLeg") as String,
                        document.get("rightLeg") as String, document.get("noise") as String)
                    newGallos.add(newGallo)
                }
                gallosList.postValue(newGallos)
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

    }



}