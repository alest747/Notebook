package com.example.bloknot

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.bloknot.databinding.ActivityEditBinding
import com.example.bloknot.db.MyDbManager
import com.example.bloknot.db.MyIntentConstants
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

    var id = 0  //переменная для опеределения id из списка при редактировании
    var isEditState = false  //переменная булиан для отслеживаня создания новой заметки в блокноте или для редактирования существующей
    val imageRequestCode = 10  //переменная с кодом запроса равная 10
    var tempImageUri = "empty"  //переменная со значением картинки
    lateinit var binding:ActivityEditBinding  //петеменная для биндинга
    private var myDbManager = MyDbManager(this)  //переменная класса MyDbManager с контекстом this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)  //биндинг активити
        setContentView(binding.root)
        getMyIntents()
    }
    override fun onResume() {
        super.onResume()
        myDbManager.openDb()  //открытие бд

    }
    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()  //закрытие бд
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK || resultCode == imageRequestCode){ //если результ код или
            binding.imEdActiv.setImageURI(data?.data)  //устанавливаем картинку
            tempImageUri = data?.data.toString()  //ури картинки
            contentResolver.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)  //добавление постоянной ссылки, исправление вылетов
        }
    }
    fun init() {
        myDbManager = MyDbManager(this)
    }

    fun onClickAddImage(view: View){  //кнопка добавления картинки
        binding.myImageLayout.visibility = View.VISIBLE  //констрейн лойаут с картинкой становится видимым
        binding.floatingActionButtonAddImage.visibility = View.GONE  //кнопка добавления картинки становится не видима
    }
    fun onClickDeleteImage(view: View){  //кнопка закрытия окна добавления картинки
        binding.myImageLayout.visibility = View.GONE  // констрейн лойаут с окном картинки становится не видимым
        binding.floatingActionButtonAddImage.visibility = View.VISIBLE  //кнопка добавления картинки становится видима
    }
    fun onClickRedactImage(view: View){

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)  //открываем документ
        intent.type = "image/*"  //картинка
        startActivityForResult(intent, imageRequestCode)  //код картинки
    }

    fun onClickSafe(view: View){  //кнопка сохранения текстов в едит текстах
        val edTitle = binding.edtTitle.text.toString()  //переменные с текстами в виде стритг
        val edDesc= binding.edtDiskrip.text.toString()

        if(edTitle != "" && edDesc != ""){  //если одно их полей пустое
            if(isEditState){  //если получили интенты, то запускаем редактирование
                myDbManager.updateItemDb(edTitle, edDesc, tempImageUri, id)  //обновляем данные в базе данных по id
            }else{
                myDbManager.insertToDb(edTitle, edDesc, tempImageUri)  //иначе добавляем данные в бд
            }
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()  // уведомление о сохранении текста
            finish()
        }else{Toast.makeText(this, "Title or Description are empty!", Toast.LENGTH_SHORT).show()}  //всплывающее уведомление при пустых полях

    }

    fun onClickEditText(view: View){  //функция кнопки редактирования текста
        binding.edtTitle.isEnabled = true  //разблокируем наш текст в полях заголовка и описания для редактирования
        binding.edtDiskrip.isEnabled = true
        binding.floatingActionButtonEditText.visibility = View.GONE  //скрываем кнопку редактирования
        binding.imageButtonRedact.visibility = View.VISIBLE  //показываем кнопку редактирования картинки
        binding.imageButtonDelete.visibility = View.VISIBLE  //показываем кнопку закрытия редактирования
        if(tempImageUri == "empty") binding.floatingActionButtonAddImage.visibility = View.VISIBLE  // показываем кнопку добавления картинки

    }

    fun getMyIntents(){  //функция принятия данных из нажатия на элемент рецикла
        binding.floatingActionButtonEditText.visibility = View.INVISIBLE

        val i = intent  //переменная равноая интенту

        if(i != null){  //если она не равна нулю

            if(i.getStringExtra(MyIntentConstants.I_TITLE_KEY) != null){  //принимаем значение по ключевому слову и если оно не равно null



                binding.floatingActionButtonAddImage.visibility = View.GONE  //прячем кнопку добавления картинки
                binding.edtTitle.setText(i.getStringExtra(MyIntentConstants.I_TITLE_KEY))  //передаем информацию на наши EdText

                isEditState = true //записываем в переменну для отслеживания редактирования значение тру при получении интентов
                binding.edtTitle.isEnabled = false  //блокируем наш текст в полях заголовка и описания
                binding.edtDiskrip.isEnabled = false
                binding.floatingActionButtonEditText.visibility = View.VISIBLE

                binding.edtDiskrip.setText(i.getStringExtra(MyIntentConstants.I_DESC_KEY))

                id = i.getIntExtra(MyIntentConstants.I_ID_KEY, 0)  //назначаем нашей переменной для обновления базы данных значение id поля редактирования

                if(i.getStringExtra(MyIntentConstants.I_URI_KEY) != "empty"){  //проверяем наличие картинки

                    binding.myImageLayout.visibility = View.VISIBLE
                    tempImageUri = i.getStringExtra(MyIntentConstants.I_URI_KEY)!!  //ссылка на установленную картинку
                    binding.imEdActiv.setImageURI(Uri.parse(tempImageUri))  //устанавливаем картинку по нашему uri
                    binding.imageButtonDelete.visibility = View.GONE
                    binding.imageButtonRedact.visibility = View.GONE


                }
            }
        }
    }
    private fun getCurrentTime():String{  //функция получения времени из календаря
        val time = Calendar.getInstance().time  //переменная с получение времени
        return time.toString()  //возпращаем переменную в типе стринг
    }
}