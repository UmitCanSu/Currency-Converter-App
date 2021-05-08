package com.example.gdg_bootcamp.VT

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAccount(accound:Account)
    @Query("Select * From accound_table Order By id ASC")
    fun readAllData():LiveData<List<Account>>
    @Update
    suspend fun updateAccount(accound: Account)
    @Delete
    suspend fun delleteAccount(accound: Account)
    @Query("Delete From accound_table")
    suspend fun deleteAllAccount()

}