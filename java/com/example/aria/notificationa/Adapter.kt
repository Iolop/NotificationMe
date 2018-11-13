package com.example.aria.notificationa

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.aria.notificationa.contract.TodoContract
import com.example.aria.notificationa.contract.TodoDBHelper
import java.text.FieldPosition

class Adapter(var data: Cursor):RecyclerView.Adapter<Adapter.todoViewHoler>(){

    val TAG: String = this@Adapter.javaClass.simpleName

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): todoViewHoler {
        val context: Context = viewGroup.context
        val todoListItemXMLId: Int = R.layout.todo_list_item
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately: Boolean = false

        val view: View = inflater.inflate(todoListItemXMLId,viewGroup,shouldAttachToParentImmediately)
        val viewHoler: todoViewHoler = todoViewHoler(view)

        return viewHoler
    }

    override fun onBindViewHolder(holder: todoViewHoler, position: Int) {
        if(!data.moveToPosition(position)){
            return
        }
        val todoThing: String = data.getString(data.getColumnIndex(TodoContract.TodoContractEntry.TODO_THING))
        val todoTime: String = data.getString(data.getColumnIndex(TodoContract.TodoContractEntry.TODO_TIME))
        val id: Long = data.getLong(data.getColumnIndex(BaseColumns._ID))
        holder.itemView.setTag(id)
        holder.tv_todothing.text = todoThing
        holder.tv_todotime.text = todoTime.substring(0,todoTime.length-3)

    }

    override fun getItemCount(): Int {
        return data.count
    }

    fun swapUI(newCursor: Cursor){
        data.close()
        data = newCursor
        this.notifyDataSetChanged()
    }

    inner class todoViewHoler(itemView: View): RecyclerView.ViewHolder(itemView){
            var tv_todothing: TextView = itemView.findViewById(R.id.tv_todo_list) as TextView
            var tv_todotime: TextView = itemView.findViewById(R.id.tv_todotime) as TextView
        }

}
