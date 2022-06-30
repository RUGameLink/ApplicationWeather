package com.example.applicationweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter(private val weather: List<String>): RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){ //Инициализация объектов лайаута айтемов ресайклера
        val tempText: TextView = itemView.findViewById(R.id.tempText)
        val feelsLikeText: TextView = itemView.findViewById(R.id.feelsLikeText)
        val humidityText: TextView = itemView.findViewById(R.id.humidityText)
        val minTempText: TextView = itemView.findViewById(R.id.minTempText)
        val maxTempText: TextView = itemView.findViewById(R.id.maxTempText)
        val windSpeedText: TextView = itemView.findViewById(R.id.windSpeedText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { //Подвязка лайаута к адаптеру ресайклера
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_item, parent, false) //Определение лайаута
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) { //Запись в айтем данных в заготовленные textview
        holder.tempText.text = weather[0]
        holder.feelsLikeText.text = weather[1]
        holder.humidityText.text = weather[2]
        holder.minTempText.text = weather[3]
        holder.maxTempText.text = weather[4]
        holder.windSpeedText.text = weather[5]
    }

    override fun getItemCount(): Int {
        return 1
    }
}