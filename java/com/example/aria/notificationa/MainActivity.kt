package com.example.aria.notificationa

import android.content.ClipData
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.example.aria.notificationa.contract.TodoContract
import com.example.aria.notificationa.contract.TodoDBHelper
import com.example.aria.notificationa.notificationService.reminderNoti
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mAdapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewInit()
        iv_addnew.setOnClickListener { startAddNewTodoThing() }
        /*SwapDelete init*/
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                val id: Long = p0.itemView.getTag() as Long
                deleteTodo(id)
                mAdapter.swapUI(getDbData())
            }
        }).attachToRecyclerView(rv_id)
    }

    override fun onResume() {
        super.onResume()
        recyclerViewInit()
    }

    fun recyclerViewInit(){
        /*RecyclerView Init*/
        var layoutmanager: LinearLayoutManager = LinearLayoutManager(this)
        rv_id.layoutManager = layoutmanager
        rv_id.setHasFixedSize(true)
        mAdapter = Adapter(getDbData())
        rv_id.adapter = mAdapter
    }

    fun startAddNewTodoThing(){
        val intent: Intent = Intent(this,AddNewTodo::class.java)
        startActivity(intent)
    }

    fun getDbData(): Cursor{
        var mDb: SQLiteDatabase = TodoDBHelper(context=this@MainActivity).readableDatabase
        val data: Cursor = mDb.query(TodoContract.TodoContractEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                TodoContract.TodoContractEntry.TODO_TIME)
        return data
    }

    fun deleteTodo(id: Long): Boolean{
        var mDb: SQLiteDatabase = TodoDBHelper(context=this@MainActivity).writableDatabase
        return mDb.delete(TodoContract.TodoContractEntry.TABLE_NAME,BaseColumns._ID + "=" +id,null)>0

    }

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}
