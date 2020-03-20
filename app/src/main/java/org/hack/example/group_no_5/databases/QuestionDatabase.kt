package org.hack.example.group_no_5.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.hack.example.group_no_5.dao.QuestionDAO
import org.hack.example.group_no_5.entities.Answer
import org.hack.example.group_no_5.entities.Question

@Database(entities = arrayOf(Question::class, Answer::class), version = 1, exportSchema = false)
abstract class QuestionDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: QuestionDatabase? = null

        fun getDatabase(context: Context): QuestionDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuestionDatabase::class.java,
                        "question_database"
                    ).allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}