package net.derohimat.footballschedule.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseHelper(ctx: Context) :
        ManagedSQLiteOpenHelper(ctx, TABLE_NAME, null, 1) {

    companion object {
        private var instance: DatabaseHelper? = null
        private const val TABLE_NAME: String = "favorite"

        fun Instance(context: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(TABLE_NAME, true,
                "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "idEvent" to TEXT + NOT_NULL,
                "event" to TEXT + NOT_NULL,
                "dateEvent" to TEXT + NOT_NULL,
                "homeTeam" to TEXT + NOT_NULL,
                "homeScore" to TEXT + NOT_NULL,
                "awayTeam" to TEXT + NOT_NULL,
                "awayScore" to TEXT + NOT_NULL
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}

val Context.database: DatabaseHelper
    get() = DatabaseHelper.Instance(applicationContext)