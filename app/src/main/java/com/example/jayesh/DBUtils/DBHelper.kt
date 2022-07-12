package com.example.jayesh.DBUtils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table " + TABLE_NAME +
                    " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME VARCHAR," +
                    "NUMBER VARCHAR," +
                    "PHOTO INTEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(name: String?, number: String?, photo: Int?): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, number)
        contentValues.put(COL_4, photo)
        val result = db.insert(TABLE_NAME, null, contentValues)
        return result != -1L
    }

    fun deleteData(name: String) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME WHERE $COL_2= '$name'")
    }

    fun deleteTable() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
    }

    val allData: Cursor
        get() {
            val db = this.writableDatabase
            return db.rawQuery("select * from $TABLE_NAME", null)
        }

    companion object {
        const val DATABASE_NAME = "contactNumbers.db"
        const val TABLE_NAME = "contacts_table"
        const val COL_1 = "ID"
        const val COL_2 = "NAME"
        const val COL_3 = "NUMBER"
        const val COL_4 = "PHOTO"
    }
}

