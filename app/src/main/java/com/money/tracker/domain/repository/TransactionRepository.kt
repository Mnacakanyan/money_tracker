package com.money.tracker.domain.repository

import com.money.tracker.data.database.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun insertTransaction(transaction: Transaction)

    fun getAllTransactions(): Flow<List<Transaction>>

    // fun getDailySummary(date: Long): Flow<YourSummaryDataClass>
    // fun getWeeklySummary(startDate: Long, endDate: Long): Flow<YourSummaryDataClass>
    // fun getMonthlySummary(month: Int, year: Int): Flow<YourSummaryDataClass>
    // fun getYearlySummary(year: Int): Flow<YourSummaryDataClass>
}
