package com.example.gdg_bootcamp.Fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.gdg_bootcamp.R
import com.example.gdg_bootcamp.VT.Account
import com.example.gdg_bootcamp.VT.AccountViewModel
import com.example.gdg_bootcamp.databinding.FragmentAccountAddBinding

class AccountAddFragment : Fragment() {

    private lateinit var binding : FragmentAccountAddBinding
    private var accountType:String = ""
    private var moneyType:String =""
    private lateinit var myViewModel : AccountViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        myViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = FragmentAccountAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.moneyType.setOnCheckedChangeListener{group, id ->
           if(id == R.id.tl_RadioButton){
               moneyType = "TL"
           }else if (id == R.id.dolar_RadioButton){
               moneyType = "DOLAR"
           }else if(id == R.id.euro_RadioButton){
               moneyType = "EURO"
           }else if(id == R.id.sterlin_RadioButton){
               moneyType = "STERLİN"
           }
        }

        binding.accountType.setOnCheckedChangeListener { radioGroup, id ->
            if(id == R.id.fature_RadioButton){
                accountType = "Fatura"
            }else if(id == R.id.kira_RadioButton){
                accountType = "Kira"
            }else if(id == R.id.diger_RadioButton){
                accountType = "Diğer"
            }
        }

        binding.ekleButton.setOnClickListener {
            buttonClicked(it)
        }
    }
    fun buttonClicked(view: View){
        val statementText =binding.aciklamaTextEdit.text.toString();
        val spendText = binding.harcamaTextEdit.text.toString();


        if(inputCheck(statementText,spendText,accountType,moneyType)){
            val account = Account(statementText,Integer.parseInt(spendText),moneyType,accountType)
            myViewModel.addAccount(account)
            var action = AccountAddFragmentDirections.actionAccountAddFragmentToMainFragment()
            Navigation.findNavController(view).navigate(action)
        }else{
            Toast.makeText(requireContext(),"Lütfen Boş Alanları Doldurun",Toast.LENGTH_LONG).show()
        }
    }
    fun inputCheck(statementText:String,spendText: String,accountType:String? ,moneyType:String? ):Boolean{

        if(accountType.equals("")){
            return false
        }else if(moneyType.equals("")){
            return false
        }
        return !(TextUtils.isEmpty(spendText)&& TextUtils.isEmpty(statementText))
    }

}