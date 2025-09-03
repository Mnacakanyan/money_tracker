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


    // fun getTransactionsByDateRange(startDate: Long, endDate: Long): Flow<List<Transaction>> {
    //     return transactionDao.getTransactionsByDateRange(startDate, endDate)
    // }

    // fun getDailySummary(date: Long): Flow<List<Transaction>> {

    // }

    // fun getWeeklySummary(startDate: Long, endDate: Long): Flow<List<Transaction>> {

    // }

    // fun getMonthlySummary(month: Int, year: Int): Flow<List<Transaction>> {

    // }

    // fun getYearlySummary(year: Int): Flow<List<Transaction>> {

    // }
}
