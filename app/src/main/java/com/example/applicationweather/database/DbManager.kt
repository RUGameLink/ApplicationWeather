package com.example.imdbapp.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DbManager (context: Context) {
    val dbHelper = DbHelper(context)
    var dataBase: SQLiteDatabase ?= null

    fun openDb(){ //Функция открытия БД
        dataBase = dbHelper.writableDatabase
    }
    fun insertToDb(title: String){//Функция записи БД
        val values = ContentValues().apply {
            put(DbName.COLUMN_NAME_TITLE, title) //Запись в бд в соответствующий столбец
        }
        dataBase?.insert(DbName.TABLE_NAME, null, values) //Запись в бд в соответствующий столбец
    }

    fun deleteToDb(title: String){//Функция удаления БД

        dataBase?.delete(DbName.TABLE_NAME, null, arrayOf(title))
    }

    @SuppressLint("Range")
    fun readDbDataTitles(): ArrayList<String>{ //Считывание из бд в лист
        val dataList = ArrayList<String>()

        val cursor = dataBase?.query(DbName.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null)
        with(cursor){
            while (this?.moveToNext()!!){ //Пока есть следующий объект
                val dataText = cursor?.getString(cursor.getColumnIndex(DbName.COLUMN_NAME_TITLE)) //Считывание в переменную из столбца
                dataList.add(dataText.toString()) //Запись в лист
            }
        }
        cursor?.close()
        return dataList //Возврат листа

    }

    fun closeDb(){//Функция закрытия БД
        dbHelper.close()
    }

    fun deleteFromDb(){
        var req = "delete from ${DbName.TABLE_NAME}"
        dataBase?.execSQL(req)

    }
}
