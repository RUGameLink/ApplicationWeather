package com.example.imdbapp.db

import android.provider.BaseColumns

object DbName: BaseColumns { //Набор констант для бд
    const val TABLE_NAME = "history" //Имя таблицы
    const val COLUMN_NAME_TITLE = "zip" //Имя колонки

    const val DATABASE_VERSION = 1 //Версия бд
    const val DATABASE_NAME = "UserHistory.db" //Название бд

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ("+
            "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_NAME_TITLE TEXT)" //Константа запроса создания таблицы

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME" //Константа запроса удаления таблицы

}