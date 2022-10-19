package com.example.bloknot.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import com.example.bloknot.MainActivity
import com.example.bloknot.R
import com.example.bloknot.databinding.FragmentMainBinding

class FragmentMain : Fragment() {

    private val dataFragModel:DataFragModel by activityViewModels()
    lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonReg = view.findViewById<Button>(R.id.buttonFragReg)
        val controller = findNavController()

        dataFragModel.message.observe(viewLifecycleOwner, {  //принимаем значение из другого фрагмента через класс через прослушиватель viewLifecycleOwner, где it принимаемое значение
                binding.tvName.text = it
            if(binding.tvName.text != "") binding.buttonFragReg.visibility = View.GONE
        })
        buttonReg.setOnClickListener{controller.navigate(R.id.fragmentReg)}


    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentMain()
    }



}


