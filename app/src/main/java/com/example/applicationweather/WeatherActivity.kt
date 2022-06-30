package com.example.applicationweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val weatherDataList = intent.getStringArrayListExtra( "weatherDataList" ) //Получение данных из другой активити

        val recyclerView: RecyclerView = findViewById(R.id.weatherView) //Подвязка ресайклера к объекту
        val linearLayoutManager = LinearLayoutManager(applicationContext)//Подготовка лайаут менеджера
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager //Инициализация лайаут менеджера
        recyclerView.adapter = CustomRecyclerAdapter(weatherDataList!!) //внесение данных из листа в адаптер (заполнение данными)

    }
}