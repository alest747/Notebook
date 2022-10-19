package com.example.bloknot.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bloknot.R
import com.example.bloknot.databinding.FragmentRegBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class FragmentReg : Fragment() {
    private val dataFragModel:DataFragModel by activityViewModels()
    lateinit var binding:FragmentRegBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_reg, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val buttonDone = view.findViewById<Button>(R.id.buttonDoneFragReg)  //кнопка принятия значений
        val controller = findNavController()  //контроллер навигатора фрагментов
        val edName = view.findViewById<TextInputEditText>(R.id.edName)  //поле ввода текста
        val edLastName = view.findViewById<TextInputEditText>(R.id.edLastName)  //поле воода текста



        buttonDone.setOnClickListener{

            if(edName.text.toString() == "" && edLastName.text.toString() ==""){  //ошибка если оба поля ввода пустые
                edName.error = "value is empty"
                edLastName.error = "value is empty"
            }
            else if(edName.text.toString() == ""){edName.error = "value is empty"}  //пошибка при пустом поле
            else if(edLastName.text.toString() == ""){ edLastName.error = "value is empty" }  //ошибка при пустом поле
            else{dataFragModel.message.value = "${edLastName.text.toString()} ${edName.text.toString()}"  //передаем данные в первый фрагмент с помощью класса
                  controller.navigate(R.id.fragmentMain)}  //переход на основной фрагмент через навигатор
        }


    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentReg()
    }
}