package com.example.applicationweather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbapp.db.DbManager
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var zipText: EditText
    private lateinit var searchButton: Button
    private lateinit var buttonDelete: Button

    private val dbManager = DbManager(this) //Вызов класса бд менеджера

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        readFromDB()

        buttonDelete.setOnClickListener {
            dbManager.openDb()
            dbManager.deleteFromDb()
            dbManager.closeDb()
            readFromDB()
        }

        searchButton.setOnClickListener { //Слушатель кнопки Узнать погоду
            if (zipText.text.isEmpty()) { //Если текстовое поле пустое, то закончить выполнение функции
                Toast.makeText(this, getText(R.string.warning), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{ //Продолжение работы в случае заполненного поля
                insertInDB(zipText.text.toString()) //Вызов метода записи запроса в бд
                readFromDB() //считывание данных из бд в ресайклер
                val thread = Thread{ //Открытие потока
                    try {
                        //Работа с api
                        val client = OkHttpClient()

                        val request = Request.Builder()
                            .url("https://weather-by-api-ninjas.p.rapidapi.com/v1/weather?city=${zipText.text}") //Внедрение введенного zip кода в запрос
                            .get()
                            .addHeader("X-RapidAPI-Host", "weather-by-api-ninjas.p.rapidapi.com") //Обращение к api
                            .addHeader("X-RapidAPI-Key", "8fac8d93edmshc4380d7d88505cdp17d5dfjsndf4c3b2501a4") //Авторазация пользователя в api
                            .build()

                        val response = client.newCall(request).execute() //Отправка запроса в api
                        val result = response.body()?.string() //Получение результатов в видо json файла
                        var error = JSONObject(result).optString("error") //Поиск и считывание заголовка с ошибкой
                        Log.d("BRUH", "$result")

                        if (error.isNotEmpty()){ //Если поле с ошибкой существует и не пустое
                            runOnUiThread { //Возврат в основной поток приложения
                                Toast.makeText(this, getText(R.string.error_message), Toast.LENGTH_SHORT).show() //Тост с ошибкой
                            }
                        }
                        else{ //Если ошибки нет, то
                            val weatherDataList: ArrayList<String> = ArrayList() //Создание листа для записей из файла
                            //Добавление записей в лист с помощью поиска по заголовкам строк
                            weatherDataList.add(JSONObject(result).getString("temp").toString())
                            weatherDataList.add(JSONObject(result).getString("feels_like").toString())
                            weatherDataList.add(JSONObject(result).getString("humidity").toString())
                            weatherDataList.add(JSONObject(result).getString("min_temp").toString())
                            weatherDataList.add(JSONObject(result).getString("max_temp").toString())
                            weatherDataList.add(JSONObject(result).getString("wind_speed").toString())

                                runOnUiThread{ //Возврат в основной поток приложения
                                val i = Intent(this, WeatherActivity::class.java) //Подготовка перехода в другую активити
                                i.putStringArrayListExtra("weatherDataList", weatherDataList) //Внесение листа с данными в интент для передачи
                                startActivity(i) //Открытие активити с результатом
                            }
                        }
                    }catch (e: Exception){
                        e.printStackTrace()
                    }

                }
                thread.start() //Открытие потока
            }
        }
    }

    private fun readFromDB(){ //Считывание данных из бд
        dbManager.openDb() //Вызов функции отрытия бд
        val historyZipData = dbManager.readDbDataTitles() //Считывание данных из бд в лист
        dbManager.closeDb() //Закрытие бд функцией

        val recyclerView: RecyclerView = findViewById(R.id.zipView) //Подвязка ресайклера к объекту
        val linearLayoutManager = LinearLayoutManager(applicationContext) //Подготовка лайаут менеджера
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager //Инициализация лайаут менеджера
        recyclerView.adapter = CustomRecyclerHistoryAdapter(historyZipData!!) //внесение данных из листа в адаптер (заполнение данными)
    }

    private fun insertInDB(text: String){ //Запись данных в бд
        dbManager.openDb()
        dbManager.insertToDb(text)
        dbManager.closeDb()
    }

    private fun init(){ //Инициализация объектов лайаута
        zipText = findViewById(R.id.zipText)
        searchButton = findViewById(R.id.searchButton)
        buttonDelete = findViewById(R.id.buttonDelete)
    }
}
