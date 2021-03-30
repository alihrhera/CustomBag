package com.customs.bag.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.customs.bag.util.call_back.OnItemClick
import com.customs.bag.R
import com.customs.bag.data.model.Tarifa
import kotlin.collections.ArrayList


public class TarifaAdapter : RecyclerView.Adapter<TarifaAdapter.TarifaHolder>() {
    private var tarifaList: List<Tarifa> = ArrayList()
    private var onItemClick: OnItemClick?=null
    fun setOnItemClick(onItemClick: OnItemClick){
        this.onItemClick=onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarifaHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.row_one_tarifa,
                parent, false
            )
        return TarifaHolder(view)
    }

    override fun onBindViewHolder(holder: TarifaHolder, position: Int) {
        val tarifa=tarifaList[position]
        holder.tarifaNumber?.text=tarifa.tarifnum
        holder.tarifdes?.text=tarifa.tarifdes
        holder.tarifsec?.text=tarifa.tarifsec
        holder.itemView.setOnClickListener{
            if (onItemClick!=null){
                onItemClick!!.onClick(tarifa)
            }
        }
    }

    override fun getItemCount(): Int {
        return tarifaList.size
    }

    fun setTarifa(tarifa: List<Tarifa>) {
        this.tarifaList = tarifa
        notifyDataSetChanged()
    }


    class TarifaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tarifaNumber: TextView? = null
        var tarifdes: TextView? = null
        var tarifsec: TextView? = null

        init {
            tarifaNumber = itemView.findViewById(R.id.tarifaNumber)
            tarifdes = itemView.findViewById(R.id.tarifdes)
            tarifsec = itemView.findViewById(R.id.tarifsec)
        }
    }


}
