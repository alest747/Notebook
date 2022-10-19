package com.example.bloknot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloknot.adapter.NoteAdapter
import com.example.bloknot.databinding.ActivityRecBinding
import com.example.bloknot.db.MyDbManager

class RecActivity : AppCompatActivity() {

    lateinit var binding:ActivityRecBinding
    private val noteAdapter = NoteAdapter(ArrayList(), this)
    private val myDbManager = MyDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecBinding.inflate(layoutInflater)  //биндинг разметки
        setContentView(binding.root)  //устанавливаем корень разметки

        init()  // функция рецикла

    }

    override fun onResume() {
        super.onResume()

        myDbManager.openDb()  //открываем бд
        fillAdapter()  //функция добавления элементов
    }

    fun init(){
        binding.recView.layoutManager = LinearLayoutManager(this@RecActivity)  //внешний вид отображения рецикла
        val swapHelper = getSwapManager()  //переменная с нашей функцией для свайпов
        swapHelper.attachToRecyclerView(binding.recView)  //соединяем функцию с нашим рециклом передавая в качестве аргумента название рецикла на разметке через биндинг
        binding.recView.adapter = noteAdapter  // назначаем элемент рецикла списку

    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()  //закрываем бд
    }

    fun onClickAdd(view:View){  //функция выбора картинки

        val intent = Intent(this, EditActivity::class.java)  //интент перехода на другое активити
        startActivity(intent)  //запуск другого активити

    }

    private fun getSwapManager():ItemTouchHelper{  //фунцкция - слушатель для нашего рецикла, присваеваем ItemTouchHelper - помошник при касании
        return ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){ // возвращаем ItemTouchHelper, присваеваем ему  свайпы влево и вправо
            override fun onMove(  //методы для ItemTouchHelper, данный для движения
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false  //так как нам не нужен слушатель для движения элемента, то отключаем его возвращая false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {  //этот метод для свайпов
                noteAdapter.removeItemList(viewHolder.adapterPosition, myDbManager)  // вызываем созданую нами функцию по удалению элемента, позиция вью холдера.позиция адаптера
            }
        })  //возвращаем ItemTouchHelper с возможглсть смахивания элемента экрана вправо или влево
    }

    fun fillAdapter(){  //функция обновления рецикла
        val list = myDbManager.readDbData()  //чтение бд
        noteAdapter.updateAdapter(list)  //обновляем наш адаптер рецикла с базы данных
        if(list.size > 0) {binding.tvEmpty.visibility = View.GONE}  //если размер листа болье 0 то скрывает Текс Вью
        else{binding.tvEmpty.visibility = View.VISIBLE}  //инача делаем Текст вью видимым
    }
}