package com.elmac.roostermanager.sis.ui.adapter

import android.annotation.SuppressLint
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elmac.roostermanager.MainApplication
import com.elmac.roostermanager.R
import com.elmac.roostermanager.data.datasource.entities.GalloEntity


class GallosAdapter:
    RecyclerView.Adapter<GallosAdapter.ViewHolder>(), View.OnCreateContextMenuListener {

    private var productsList= emptyList<GalloEntity>()
    //val context= MainAplication.appContext

    @SuppressLint("NotifyDataSetChanged")
    fun setListGallos(products: List<GalloEntity>){
        this.productsList = products
        notifyDataSetChanged()
    }

    private var position:Int = 0

    fun getPosition():Int{
        return position
    }

    fun setPosition(position: Int){
        this.position=position
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.desc_gallo_element)
        val description: TextView = view.findViewById(R.id.mark_list)
        val gender: ImageView = view.findViewById(R.id.gender_img)
        var lastPosition = -1

        private fun setAnimation(viewToAnimate: View, position: Int) {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition) {
                val animation: Animation =
                    AnimationUtils.loadAnimation(MainApplication.appContext, android.R.anim.slide_in_left)
                viewToAnimate.startAnimation(animation)
                lastPosition = position
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_info_card, viewGroup, false)
        view.setOnCreateContextMenuListener(this)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text = productsList[position].line
        viewHolder.description.text =productsList[position].plaque.toString()
        viewHolder.gender.setImageResource(R.drawable.ic_gallo)
        viewHolder.itemView.setOnLongClickListener {
            setPosition(position)
            false
        }
    }

    override fun getItemCount() = productsList.size

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        view: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu!!.setHeaderTitle(null);
        menu.add(0, view!!.id, 0, "Ver detalles");//groupId, itemId, order, title
        menu.add(0, view.id, 0, "Eliminar")
        menu.add(0, view.id, 0, "Editar");
    }

}