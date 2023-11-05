package com.abhinav12k.MixdUp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abhinav12k.MixdUp.data.local.dao.DrinkCardDao
import com.abhinav12k.MixdUp.domain.model.DrinkCard

@Database(entities = [DrinkCard::class], version = 1)
abstract class MixdUpDatabase : RoomDatabase() {

    abstract fun drinkCardDao(): DrinkCardDao

    companion object {
        private const val DB_NAME = "MixdUp_db"

        @Volatile
        private var INSTANCE: MixdUpDatabase? = null

        fun getInstance(context: Context): MixdUpDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MixdUpDatabase::class.java, DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}