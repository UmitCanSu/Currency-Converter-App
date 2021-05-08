package com.example.gdg_bootcamp.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.gdg_bootcamp.Fragment.ChanceNameFragmentDirections
import com.example.gdg_bootcamp.R
import com.example.gdg_bootcamp.databinding.FragmentChanceNameBinding

class ChanceNameFragment : Fragment() {

    var gender:String =""
    lateinit var binding :FragmentChanceNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentChanceNameBinding.inflate(this.layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioGroupGender.setOnCheckedChangeListener{ gruop, id ->
            if(id == R.id.erkekRadio){
                gender = "Erkek"
            }else if(id == R.id.kadınRadio){
                gender = "Kadın"
            }else{
                gender = "Belirtmek İstemiyorum"
            }
        }
        binding.button.setOnClickListener{
          if(gender.length>0  && binding.nameChancedTextEdit.length()  >0 ){
              var sharedPreferences =requireActivity().getSharedPreferences("com.example.gdg_bootcamp",
                  Context.MODE_PRIVATE)
              val editor = sharedPreferences.edit()
              editor.putString("name", binding.nameChancedTextEdit.text.toString() );
              editor.putString("gender",gender)
              editor.apply()
              sharedPreferences.edit();


              var action = ChanceNameFragmentDirections.actionChanceNameFragmentToMainFragment();
              Navigation.findNavController(it).navigate(action)
          }else{
              Toast.makeText(requireContext(),"Lütfen Boş Alan Bırakmayın",Toast.LENGTH_LONG).show()
          }
        }
    }


}