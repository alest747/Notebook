package com.example.bloknot.adapter
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bloknot.EditActivity
import com.example.bloknot.R
import com.example.bloknot.databinding.NoteItemBinding
import com.example.bloknot.db.ListItem
import com.example.bloknot.db.MyDbManager
import com.example.bloknot.db.MyIntentConstants

class NoteAdapter(listNote:ArrayList<ListItem>, contextA:Context):RecyclerView.Adapter<NoteAdapter.NoteHolder>() {  //Адаптер рецикла, передаем Массив из нашего класса ListItem, передаем контекст из Активити

    var noteArray = listNote  //массив из класса
    var context = contextA  //контекст из активити

    class NoteHolder(itemView:View, contextV:Context):RecyclerView.ViewHolder(itemView){

        val context = contextV  //конткст холдера Вью
        val tvTitle = itemView.findViewById<TextView>(R.id.tvRecNoteItem)  //Едит текст для отображения инфы

        fun setData(item:ListItem){  //функция установки значений в нашу заготовку для рецикла, присваеваем наш класс ListItem
            tvTitle.text = item.title  //заголовок из класса
            itemView.setOnClickListener{  //слушатель нажатий в рецикл

                val intent = Intent(context, EditActivity::class.java).apply { //переход на другое активити при нажатии

                    putExtra(MyIntentConstants.I_TITLE_KEY, item.title)  //помещаем данные из базы данных через константы в интент
                    putExtra(MyIntentConstants.I_DESC_KEY, item.desc)
                    putExtra(MyIntentConstants.I_URI_KEY, item.uri)
                }
                context.startActivity(intent)  //открываем новое активити


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {  //холдер - держадель разметки
        val inflater = LayoutInflater.from(parent.context)  //надуваем разметку
        return NoteHolder(inflater.inflate(R.layout.note_item, parent, false), context)  //шаборн для рецикла
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {  //то, что должно быть на разметке
        holder.setData(noteArray[position])  //позиция
    }

    override fun getItemCount(): Int {
       return noteArray.size  //размер массива
    }

    fun updateAdapter(listItems:List<ListItem>){  //функция обновления адаптера, присваеваем наш класс ListItem
        noteArray.clear()  //чистим
        noteArray.addAll(listItems)  //добавляем новые данные
        notifyDataSetChanged()  //оповещение об изменениях
    }

    fun removeItemList(position:Int, dbManager: MyDbManager){  //функция удаления элемента из рецикла, передаем в него позицию равную Int, передаем MyDbManager для удаления элементов из базы данных

        dbManager.removeItemFromDb(noteArray[position].id.toString())  //удаляем позицию в базе данных по id
        noteArray.removeAt(position) //удаляем элемент из массива по позиции
        notifyItemRangeChanged(0,noteArray.size)  //изменение диапазона массива, начинается с нуля, а заканчивается фактическим размером массива
        notifyItemRemoved(position)  //извещаем об удалении элемента
    }
}