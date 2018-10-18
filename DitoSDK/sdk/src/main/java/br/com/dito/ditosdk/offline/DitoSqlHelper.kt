package br.com.dito.ditosdk.offline

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

internal class DitoSqlHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "dito") {

    companion object {
        private var instance: DitoSqlHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DitoSqlHelper {
            if (instance == null) {
                instance = DitoSqlHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {

        db.createTable("Identify", true,
                "_id" to TEXT + PRIMARY_KEY,
                "json" to TEXT,
                "reference" to  NULL,
                "send" to INTEGER + DEFAULT("0"))

        db.createTable("Event", true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "json" to TEXT,
                "retry" to INTEGER + DEFAULT("1"))

        db.createTable("NotificationRead", true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "json" to TEXT,
                "retry" to INTEGER + DEFAULT("1"))
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

}
