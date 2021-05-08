package com.example.gdg_bootcamp.VT

import androidx.lifecycle.LiveData

class AccountRepository(private val accountDao: AccountDao) {
    val readAllData : LiveData<List<Account>> = accountDao.readAllData()

    suspend fun addAccount(account: Account){
        accountDao.addAccount(account)
    }
    suspend fun updateAccount(account: Account) {
        accountDao.updateAccount(account)
    }
    suspend fun deleteAccount(account: Account){
        accountDao.delleteAccount(account)
    }
    suspend fun deleteAllAcount(){
        accountDao.deleteAllAccount()
    }
}