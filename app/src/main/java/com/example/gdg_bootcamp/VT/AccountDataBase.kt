package com.example.gdg_bootcamp.VT

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Account::class],version = 1,exportSchema = false)
abstract class AccountDataBase:RoomDatabase() {

    abstract fun accoundDao():AccountDao
    companion object{
        private var INSTANCE : AccountDataBase ?= null
        fun getDatabase(context: Context):AccountDataBase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AccountDataBase::class.java,
                    "accont_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}