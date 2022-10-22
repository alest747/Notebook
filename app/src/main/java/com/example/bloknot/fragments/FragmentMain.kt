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
import com.squareup.picasso.Picasso

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
        val imageUrl = "https://downloader.disk.yandex.ru/preview/353e2ecde9315ddf4c6fa821d2c78d90a4d204cd617e657b580433cdeb42cf47/6353fe81/tP46TFixCVFUFs_ptYImI4R4oup6S_cILJHwDRbjiiu4XKKQI4oq9C2tLCJm_G5g-pPUgKmPxHx_jC7m6T6zmg%3D%3D?uid=0&filename=images_noteRebild3.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=2048x2048" //загружаем картинку с помощью пикассо и интернета
        Picasso.get().load(imageUrl).into(binding.imageViewFrafMain)


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


