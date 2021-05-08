package com.example.gdg_bootcamp.Fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.gdg_bootcamp.R
import com.example.gdg_bootcamp.Fragment.SplashScreenDirections

class SplashScreen : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({

            var sharedPreferences =requireActivity().getSharedPreferences("com.example.gdg_bootcamp",Context.MODE_PRIVATE)
            var boolean:Boolean = sharedPreferences.getBoolean("OnBoarCheck",true)
            if(boolean){

                val action = SplashScreenDirections.actionSplashScreenToOnBoardFragment()
                Navigation.findNavController(view).navigate(action)

            }else{

                val action = SplashScreenDirections.actionSplashScreenToMainFragment()
                Navigation.findNavController(view).navigate(action)



            }


        },5000)

    }


}