package com.elmac.roostermanager.sis.ui.view.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.elmac.roostermanager.R
import com.elmac.roostermanager.data.datasource.entities.GalloEntity
import com.elmac.roostermanager.databinding.ActivityRegisterGallosBinding
import com.elmac.roostermanager.sis.ui.adapter.SpinnerAdapter
import com.elmac.roostermanager.sis.viewmodel.RegisterGallosViewModel


class RegisterGallosActivity : AppCompatActivity() {

    companion object{
        const val ID = "id"
    }

    lateinit var binding: ActivityRegisterGallosBinding
    private val registerGallosViewModel:RegisterGallosViewModel by viewModels()
    var dataGallo= GalloEntity(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterGallosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObservables()
        setInitDataGallo()
        initValuesSpinner()

        binding.guardarBtn.setOnClickListener { saveGallo()}

    }

    private fun setInitDataGallo(){
        val id = intent.getIntExtra(ID,0)
        if(id!=0){
            registerGallosViewModel.setInitValues(id)
        }
    }

    private fun saveGallo(){
        getNewGalloData()
        registerGallosViewModel.validarData(dataGallo)
    }

    private fun setObservables(){
        registerGallosViewModel.gallo.observe(this, Observer {
            dataGallo=it
            setFormWithInitValues()
        })

        registerGallosViewModel.fragment.observe(this, Observer {
            finish()
        })
    }

    private fun getNewGalloData(){
        dataGallo.line = binding.lienaValue.text.toString()
        dataGallo.year = binding.yearValue.text.toString()
        dataGallo.plaque = binding.placaValue.text.toString()
        dataGallo.ring = binding.ringValue.text.toString()
        dataGallo.leftLeg = binding.spPataIzquierda.selectedItem.toString()
        dataGallo.rightLeg = binding.spPataDerecha.selectedItem.toString()
        dataGallo.noise = binding.spNariz.selectedItem.toString()
        dataGallo.gender = binding.spGenero.selectedItem.toString()
        dataGallo.id=0
    }

    private fun setFormWithInitValues(){
        binding.lienaValue.setText(dataGallo.line)
        binding.yearValue.setText(dataGallo.year.toString())
        binding.placaValue.setText(dataGallo.plaque)
        binding.ringValue.setText(dataGallo.ring)
    }

    private fun initValuesSpinner(){
        val legImages = listOf(R.drawable.pata_ninguna_, R.drawable.pata_afuera_,
            R.drawable.pata_adentro_, R.drawable.pata_ambas_)
        val noiseImage= listOf(R.drawable.nariz_ninguna_, R.drawable.nariz_derecha_,
            R.drawable.nariz_izquierda_, R.drawable.nariz_ambas_)
        val genderImage= listOf(R.drawable.ic_gallo, R.drawable.ic_gallina_mini)
        binding.spPataIzquierda.apply {
            adapter= SpinnerAdapter(applicationContext, legImages)
            setSelection(1,false)
        }
        binding.spPataDerecha.apply {
            adapter= SpinnerAdapter(applicationContext, legImages)
            setSelection(1,false)
        }
        binding.spNariz.apply {
            adapter= SpinnerAdapter(applicationContext, noiseImage)
            setSelection(1,false)
        }
        binding.spGenero.apply {
            adapter= SpinnerAdapter(applicationContext, genderImage)
            setSelection(1,false)
        }
    }

}