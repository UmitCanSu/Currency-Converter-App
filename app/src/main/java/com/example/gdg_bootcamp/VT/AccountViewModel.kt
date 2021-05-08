package com.example.gdg_bootcamp.VT

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(application: Application) :AndroidViewModel(application) {

    val readAllData : LiveData<List<Account>>
    private  val repository :AccountRepository

    init {
        val accountDao = AccountDataBase.getDatabase(application).accoundDao()
        repository = AccountRepository(accountDao)
        readAllData = repository.readAllData
    }
    fun addAccount(account: Account){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAccount(account)
        }
    }
    fun updateAccount(account: Account){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateAccount(account)
        }
    }
    fun deleteAccount(account: Account){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAccount(account)
        }
    }
    fun deleteAllAccount(account: Account){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAllAcount()
        }
    }




}