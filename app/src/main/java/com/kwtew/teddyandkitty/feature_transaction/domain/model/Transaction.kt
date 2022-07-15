package com.kwtew.teddyandkitty.feature_transaction.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val mode: String, //RT, WT, WR, RW, TR, TW
    @ColumnInfo(name = "transaction_timestamp") val transactionTimestamp: Long,
    @ColumnInfo(name = "created_timestamp") val createdTimestamp: Long,
    @ColumnInfo(name = "updated_timestamp") val updatedTimestamp: Long,
    val venue: String? = null,
    val product: String? = null,
    @ColumnInfo(name = "third_party_name") val thirdPartyName: String? = null,
    val amount: Double,
    val remark: String? = null
)
