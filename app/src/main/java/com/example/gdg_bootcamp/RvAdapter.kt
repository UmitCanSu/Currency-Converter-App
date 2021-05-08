package com.example.gdg_bootcamp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gdg_bootcamp.Model.CurrencyModel
import com.example.gdg_bootcamp.VT.Account
import com.example.gdg_bootcamp.databinding.RvRowBinding

class RvAdapter(private val contex:Context, private val accountList: List<Account>,
                private val moneyType:String, private val listener:Listener, private val currency: CurrencyModel,
                private val spendMoney:TextView) :
    RecyclerView.Adapter<RvAdapter.CardViewNesneTutucu>() {

    interface Listener {
        fun onItemClick(account: Account,view: View)
    }

    var money:Float=0f;
    class CardViewNesneTutucu(val binding:RvRowBinding ):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewNesneTutucu {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_row,parent,false)
        return CardViewNesneTutucu(RvRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CardViewNesneTutucu, position: Int) {
        var account = accountList[position]
        if(account.harcamaType == "Fatura"){
            holder.binding.rvImage.setImageDrawable(contex.getDrawable(R.drawable.bill))
        }else if(account.harcamaType == "Kira"){
            holder.binding.rvImage.setImageDrawable(contex.getDrawable(R.drawable.home))
        }else{
            holder.binding.rvImage.setImageDrawable(contex.getDrawable(R.drawable.basket))
        }
        //holder.spendText.text = account.harcama.toString()+" "+ moneyType

        moneyConverter(holder,account)
        holder.binding.stantmentText.text = account.aciklama
        holder.binding.cardViewRow.setOnClickListener{
            listener.onItemClick(account,it)
        }
    }

    override fun getItemCount(): Int {
        return accountList.size
    }
    private fun moneyConverter(holder: CardViewNesneTutucu, account: Account){
        var moneyCode = "TRY"
        if(account.paraBirimi.equals("EURO")){
            moneyCode = "EUR"
        }else if(account.paraBirimi.equals("DOLAR")){
            moneyCode = "USD"
        }else if(account.paraBirimi.equals("STERLİN")){
            moneyCode = "GBP"
        }else{
            moneyCode = "TRY"
        }

        var currencyNewFormatAgo = currency.conversion_rates.get(moneyCode).toString().toFloat()
        var currencyFormatAfter = String.format("%.2f",currencyNewFormatAgo)
        var result = account.harcama / currencyFormatAfter.toFloat()
        var ressultnewFormat= String.format("%.2f",result.toString().toFloat()).toFloat()
        money = money  + ressultnewFormat
        spendMoney.text= "Harcamanız \n"+"${String.format("%.2f",money)} ${moneyType}";
        holder.binding.spendingText.text = ressultnewFormat.toString()+ moneyType



    }

}