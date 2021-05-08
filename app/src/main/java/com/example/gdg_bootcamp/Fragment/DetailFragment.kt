package com.example.gdg_bootcamp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.gdg_bootcamp.R
import com.example.gdg_bootcamp.VT.Account
import com.example.gdg_bootcamp.VT.AccountViewModel
import com.example.gdg_bootcamp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    lateinit var mAccoundModelView:AccountViewModel
    var moneyTypeString ="₺"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mAccoundModelView = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var account:Account  ?= null
        arguments?.let {
            account = DetailFragmentArgs.fromBundle(it).currentAccount
            if(account!!.harcamaType == "Fatura"){
                binding.image.setImageDrawable(requireActivity().getDrawable(R.drawable.bill))
            }else if(account!!.harcamaType == "Kira"){
                binding.image.setImageDrawable(requireActivity().getDrawable(R.drawable.home))
            }else{
                binding.image.setImageDrawable(requireActivity().getDrawable(R.drawable.basket))
            }
            moneyTypeString=  DetailFragmentArgs.fromBundle(it).moneyType
            binding.stantmentText.text = account!!.aciklama
            binding.spendingText.text =  account!!.harcama.toString()+" "+ DetailFragmentArgs.fromBundle(it).moneyType
        }
        binding.silButton.setOnClickListener {

            if(account != null){
                mAccoundModelView.deleteAccount(account!!)
                var action = DetailFragmentDirections.actionDetailFragmentToMainFragment(moneyTypeString )
                Navigation.findNavController(it).navigate(action)
            }
        }
        binding.backButton.setOnClickListener {
            var action = DetailFragmentDirections.actionDetailFragmentToMainFragment(moneyTypeString)
            Navigation.findNavController(it).navigate(action)
        }
    }



}