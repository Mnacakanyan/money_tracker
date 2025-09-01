package com.money.tracker.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: Transaction)

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    // You can add more specific queries here later, for example:
    // @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    // fun getTransactionsByDateRange(startDate: Long, endDate: Long): Flow<List<Transaction>>

    // @Query("SELECT SUM(amount) FROM transactions WHERE type = :type AND date BETWEEN :startDate AND :endDate")
    // suspend fun getTotalAmountByTypeAndDateRange(type: String, startDate: Long, endDate: Long): Double

    // @Query("SELECT SUM(amount) FROM transactions WHERE date = :date")
    // suspend fun getDailyTotal(date: Long): Double
}
