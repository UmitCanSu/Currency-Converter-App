package com.example.gdg_bootcamp.Fragment

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color.BLACK
import android.graphics.Color.YELLOW
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gdg_bootcamp.Services.CurrencyAPI
import com.example.gdg_bootcamp.Model.CurrencyModel
import com.example.gdg_bootcamp.RvAdapter
import com.example.gdg_bootcamp.VT.Account
import com.example.gdg_bootcamp.Model.AccountViewModel
import com.example.gdg_bootcamp.databinding.FragmentMainBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class MainFragment : Fragment(),RvAdapter.Listener {
    lateinit var binding:FragmentMainBinding
    lateinit var mAccountView: AccountViewModel
    var moneyType = "₺"
    var moneyCode ="TRY"
    var spendMoney = 0;
    lateinit var buttonList:ArrayList<Button>
    private val BASE_URL= "https://v6.exchangerate-api.com/v6/"
    lateinit var currencyModel: CurrencyModel
    lateinit var sharedPareferences :SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPareferences = requireActivity().getSharedPreferences("com.example.gdg_bootcamp.Fragment",Context.MODE_PRIVATE)
        arguments?.let {
            moneyType = MainFragmentArgs.fromBundle(it).moneyType
            println("(((((("+moneyType)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        binding.dolarButton.isSelected()
        mAccountView = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences =  requireActivity().getSharedPreferences("com.example.gdg_bootcamp",Context.MODE_PRIVATE)
        binding.spendingText.text = "Harcamanız \n ${spendMoney} $moneyType"
        var name:String =""
        buttonList = ArrayList<Button>()
        buttonList.add(binding.sterlinButton)
        buttonList.add(binding.tlButton)
        buttonList.add(binding.euroButton)
        buttonList.add(binding.dolarButton)
        if(sharedPreferences.getString("gender","null").equals("Erkek")){
            name = sharedPreferences.getString("name","null")+ " Bey"
        }else if(sharedPreferences.getString("gender","null").equals("Kadın")){
            name = sharedPreferences.getString("name","null")+ " Hanım"
        }else if(sharedPreferences.getString("gender","null").equals("Belirtmek İstemiyorum")){
            name = sharedPreferences.getString("name","null").toString()
        }else{
            name = "Lütfen isminizi giriniz"
        }

        binding.nameText.text = "Merhaba \n"+name

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        println("--<"+moneyType)
        selectedButton()
/*
        mAccountView.readAllData.observe(requireActivity(), androidx.lifecycle.Observer {
            var action = MainFragmentDirections
            var adapter  = RvAdapter(requireContext(),it,"Tl",this@MainFragment,currencyModel)
            binding.recyclerView.adapter = adapter
        })
*/

        binding.ekleButton.setOnClickListener {
            var action = MainFragmentDirections.actionMainFragmentToAccountAddFragment()
            Navigation.findNavController(it).navigate(action)
        }
        binding.tlButton.setOnClickListener {
            buttonClick(it)
        }
        binding.dolarButton.setOnClickListener {
            buttonClick(it)
        }
        binding.euroButton.setOnClickListener {
            buttonClick(it)
        }
        binding.sterlinButton.setOnClickListener {
            buttonClick(it)
        }
        binding.cardViewInfomation.setOnClickListener {
            var action = MainFragmentDirections.actionMainFragmentToChanceNameFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun onItemClick(account: Account, view: View) {
        var action = MainFragmentDirections.actionMainFragmentToDetailFragment(account,moneyType)
        Navigation.findNavController(view).navigate(action)
    }
    fun buttonClick(view: View){

        val button:Button = view.findViewById(view.id)
        if(button.text.equals("EURO")){
            moneyType = "€";
            moneyCode = "EUR"
        }else if(button.text.equals("DOLAR")){
            moneyType = "$";
            moneyCode = "USD"
        }else if(button.text.equals("STERLİN")){
            moneyType = "£";
            moneyCode = "GBP"
        }else{
            moneyType = "₺";
            moneyCode ="TRY"
        }
        println("Button Tiklandi")

        for (button2 in buttonList){
            if(button2.id ==button.id ){
                button2.setTextColor(YELLOW)
            }else{
                button2.setTextColor(BLACK)
            }
        }



        loadData(moneyCode,moneyType)



    }
    fun selectedButton(){
        println("-->"+moneyType+"------"+moneyCode)
        for (button in buttonList){
            if(button.hint.equals(moneyType)){
                button.setTextColor(YELLOW)

                if(moneyType.equals("€")){
                    moneyCode = "EUR"
                }else if(moneyType.equals("$")){
                    moneyCode = "USD"
                }else if(moneyType.equals("£")){
                    moneyCode = "GBP"
                }else {
                    moneyCode ="TRY"
                }
            }else{
                button.setTextColor(BLACK)
            }
            loadData(moneyCode,moneyType)
        }
    }
    private fun loadData(type:String,moneyType:String){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CurrencyAPI::class.java)
        var call = service.getTLData()
        if(type.equals("EUR")){
            call = service.getEuroData()
        }else if(type.equals("USD")){
            call = service.getDolarData()
        }else if(type.equals("GBP")){
            call = service.getSterlinData()
        }else{
            call = service.getTLData()
        }
        call.enqueue(object : Callback<CurrencyModel>{
            override fun onResponse(call: Call<CurrencyModel>, response: Response<CurrencyModel>) {
                if(response.isSuccessful){
                    response.body()?.let {

                        if(it != null){
                            currencyModel=it

                            var editor =sharedPareferences.edit()
                            val json = Gson().toJson(it)
                            editor.putString(type,json)
                            editor.apply()
                            sharedPareferences.edit()
                            mAccountView.readAllData.observe(requireActivity(), androidx.lifecycle.Observer {
                                var action = MainFragmentDirections
                                var adapter  = RvAdapter(requireContext(),it,moneyType,this@MainFragment,currencyModel,binding.spendingText)
                                binding.recyclerView.adapter = adapter
                            })
                        }
                    }
                }
            }

            override fun onFailure(call: Call<CurrencyModel>, t: Throwable) {
                t.printStackTrace()
                println("Hata")
                 val json = sharedPareferences.getString(type,"")
                if(!json.equals("")){
                    var currencyModel: CurrencyModel = Gson().fromJson(json, CurrencyModel::class.java)
                    mAccountView.readAllData.observe(requireActivity(), androidx.lifecycle.Observer {
                        var action = MainFragmentDirections
                        var adapter  = RvAdapter(requireContext(),it,moneyType,this@MainFragment,currencyModel,binding.spendingText)
                        binding.recyclerView.adapter = adapter
                    })
                }


            }

        })
    }



}