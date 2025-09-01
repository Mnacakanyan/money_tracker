package com.money.tracker.data.repository

import com.money.tracker.data.database.Transaction
import com.money.tracker.data.database.TransactionDao
import com.money.tracker.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
): TransactionRepository {

    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insert(transaction)
    }

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions()
    }

    // You can add more methods here as needed, for example:
    // fun getTransactionsByDateRange(startDate: Long, endDate: Long): Flow<List<Transaction>> {
    //     return transactionDao.getTransactionsByDateRange(startDate, endDate)
    // }

    // fun getDailySummary(date: Long): Flow<List<Transaction>> { // Or a custom summary object
    //     // Logic to calculate daily summary based on transactions for the given date
    //     // This might involve querying transactions for a specific day and then processing them.
    // }

    // fun getWeeklySummary(startDate: Long, endDate: Long): Flow<List<Transaction>> { // Or a custom summary object
    //     // Logic for weekly summary
    // }

    // fun getMonthlySummary(month: Int, year: Int): Flow<List<Transaction>> { // Or a custom summary object
    //     // Logic for monthly summary
    // }

    // fun getYearlySummary(year: Int): Flow<List<Transaction>> { // Or a custom summary object
    //     // Logic for yearly summary
    // }
}
