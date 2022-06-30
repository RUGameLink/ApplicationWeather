package com.example.applicationweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerHistoryAdapter(private val zip: List<String>): RecyclerView.Adapter<CustomRecyclerHistoryAdapter.MyViewHolderHistory>() {
    class MyViewHolderHistory(itemView: View) : RecyclerView.ViewHolder(itemView){ //Инициализация объектов лайаута айтемов ресайклера
        val historyView: TextView = itemView.findViewById(R.id.historyView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderHistory { //Подвязка лайаута к адаптеру ресайклера
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_history_item, parent, false) //Определение лайаута
        return MyViewHolderHistory(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolderHistory, position: Int) { //Запись в айтем данных в заготовленные textview
        holder.historyView.text = zip[position]
    }

    override fun getItemCount(): Int {
        return zip.size
    }
}
