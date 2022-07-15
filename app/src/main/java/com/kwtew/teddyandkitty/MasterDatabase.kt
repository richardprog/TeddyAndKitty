package com.kwtew.teddyandkitty

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kwtew.teddyandkitty.feature_transaction.data.data_source.TransactionDao
import com.kwtew.teddyandkitty.feature_transaction.domain.model.Transaction

@Database(
    entities = [Transaction::class],
    version = 2,
    exportSchema = false
)
abstract class MasterDatabase : RoomDatabase() {
    abstract val dao : TransactionDao

    companion object {
        const val DATABASE_NAME = "master_db"
    }
}