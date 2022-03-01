package com.example.gdg_bootcamp.Fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.gdg_bootcamp.Fragment.OnBoardFragmentDirections
import com.example.gdg_bootcamp.R
import com.example.gdg_bootcamp.databinding.FragmentOnBoardBinding
import java.util.*

class OnBoardFragment : Fragment() {

    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var textList: ArrayList<String>
    private lateinit var animList: ArrayList<Int>
    private lateinit var binding: FragmentOnBoardBinding
    private var counter = 0;
    private lateinit var promotionTextList :ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textList = ArrayList<String>()
        textList.add("Karşınızda Harcama Takip Uygulaması ")
        textList.add("Kolayca Kategorilendir ")
        textList.add("Kolayca Kategorilendir    ")

        promotionTextList = ArrayList<String>()
        promotionTextList.add("Artık karışıkılığa son")
        promotionTextList.add("Harcamalarınızı kategorileyerek daha kola takip edin!")
        promotionTextList.add("Harcamalarınızı kategorileyerek daha kola takip edin!")


        animList = ArrayList<Int>()
        animList.add(R.raw.mixed)
        animList.add(R.raw.e_ticaret)
        animList.add(R.raw.money)

        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardBinding.inflate(layoutInflater)

        return binding.root //inflater.inflate(R.layout.fragment_on_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            binding.textView.text = textList[counter];
            binding.lottieAnimationView.setAnimation(animList[counter])
            binding.button.setOnClickListener {
                counter++;
                if(animList.size != counter) {
                    binding.textView.text = textList[counter]
                    binding.lottieAnimationView.setAnimation(animList[counter])
                    binding.promotionText.text = promotionTextList[counter]

                    binding.lottieAnimationView.playAnimation()
                    binding.lottieAnimationView.loop(true)


                    if (animList.size - 1 == counter) {
                        binding.button.text = "bitir"
                    }
                }else{
                    var sharedPreferences = requireActivity().getSharedPreferences("com.example.gdg_bootcamp",Context.MODE_PRIVATE)
                    val editor =sharedPreferences.edit();
                    editor.putBoolean("OnBoarCheck",false);
                    editor.apply();

                    var action =
                        OnBoardFragmentDirections.actionOnBoardFragmentToChanceNameFragment()
                    Navigation.findNavController(it).navigate(action)
                }

        }
        }

    }

