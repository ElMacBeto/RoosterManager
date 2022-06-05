package com.elmac.roostermanager.sis.ui.view.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.elmac.roostermanager.R
import com.elmac.roostermanager.data.datasource.entities.GalloEntity
import com.elmac.roostermanager.databinding.FragmentGallosHomeBinding
import com.elmac.roostermanager.sis.ui.adapter.GallosAdapter
import com.elmac.roostermanager.sis.viewmodel.GallosHomeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class GallosHomeFragment : Fragment() {

    private var _binding: FragmentGallosHomeBinding? = null
    private val binding get() = _binding!!
    private val gallosHomeViewModel:GallosHomeViewModel by viewModels()
    private var gallos= mutableListOf<GalloEntity>()
    private lateinit var adapter: GallosAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGallosHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setObservales()
        //gallosHomeViewModel.getAllGallos()
        gallosHomeViewModel.loadGallosFirebase()
        activity?.findViewById<FloatingActionButton>(R.id.add_btn)!!.setOnClickListener{
            gallosHomeViewModel.changeToRegisterActivity(requireContext())
        }

    }

    private fun initRecyclerView(){
//        val gallo= GalloEntity(2,"giro",2020,"15g", "20f")
//        gallosHomeViewModel.addNewGallo(gallo)
        adapter = GallosAdapter()
        adapter.setListGallos(gallos)
        binding.recyclerViewList.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewList.adapter = adapter
    }

    private fun setObservales(){
        gallosHomeViewModel.gallosList.observe(viewLifecycleOwner, Observer {
            gallos.clear()
            gallos = it
            adapter.setListGallos(gallos)
        })
    }


    override fun onResume() {
        super.onResume()
//        gallosHomeViewModel.getAllGallos()
        gallosHomeViewModel.loadGallosFirebase()
    }


}