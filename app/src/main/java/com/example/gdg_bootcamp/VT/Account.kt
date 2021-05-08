package com.example.gdg_bootcamp.VT

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Parcelize
@Entity(tableName = "accound_table")
data class Account (
    val aciklama:String,
    val harcama:Int,
    val paraBirimi:String,
    val harcamaType:String
      ):Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}