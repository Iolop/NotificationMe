package com.example.aria.notificationa

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TimePicker
import com.example.aria.notificationa.contract.TodoContract
import com.example.aria.notificationa.contract.TodoDBHelper
import kotlinx.android.synthetic.main.todo_add_new.*
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.Toast
import com.example.aria.notificationa.notificationService.reminderNoti
import java.util.*
import kotlin.math.min

class AddNewTodo: AppCompatActivity() {
    /*To make sure store in timestamp
    * date:xxxx-xx-xx
    * time:xx:xx:xx
    * */
    var mDb: SQLiteDatabase?=null
    var date: String?=null
    var time: String?=null
    lateinit var todoThing: String
    var twoNumFormatString = "%02d"
    var REQUEST_ID: Int = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todo_add_new)

        iv_addnew_finish.setOnClickListener { addNewTodoThingToDB() }
        bu_addday.setOnClickListener { showChooseDay() }
        bu_addtime.setOnClickListener { showChooseTime() }
    }


    fun addNewTodoThingToDB()
    {
        if (!checkData()){
            Toast.makeText(this@AddNewTodo,"Plz set time",Toast.LENGTH_LONG).show()

        }
        else if (!checkThing()){
            Toast.makeText(this@AddNewTodo,"Plz set things",Toast.LENGTH_LONG).show()
        }
        else if (!checkDate()) {
            Toast.makeText(this@AddNewTodo,"Plz set a correct time",Toast.LENGTH_LONG).show()
        }
        else{
            storeDate()
            finish()
        }
    }

    fun checkDate(): Boolean{
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int =  c.get(Calendar.MONTH)
        val day: Int =  c.get(Calendar.DAY_OF_MONTH)
        val hour: Int = c.get(Calendar.HOUR_OF_DAY)
        val minute: Int = c.get(Calendar.MINUTE)
        val timeNow : String = "%d-%02d-%02d %02d:%02d:00".format(year,month+1,day,hour, minute)
        val todoTime: String = date + " " + time
        return (todoTime > timeNow)

    }
    fun registerAlarm(todoTime: String,things: String){
        val alarm: Intent = Intent(this@AddNewTodo,reminderNoti::class.java)
        alarm.putExtra("TODO_TIME",todoTime)
        alarm.putExtra("TODO_THING",things)
        startService(alarm)
    }

    fun storeDate(){
        val dbHelper: TodoDBHelper = TodoDBHelper(context = this@AddNewTodo)
        mDb = dbHelper.writableDatabase

        todoThing = (window.findViewById(R.id.ev_todothing) as EditText).text.toString()
        val todoTime: String = date + " " + time
        var cv: ContentValues = ContentValues()
        cv.put(TodoContract.TodoContractEntry.TODO_THING, todoThing)
        cv.put(TodoContract.TodoContractEntry.TODO_TIME, todoTime)
        mDb?.insert(TodoContract.TodoContractEntry.TABLE_NAME, null, cv)
        registerAlarm(todoTime,todoThing)

    }

    fun checkThing(): Boolean{
        return ((window.findViewById(R.id.ev_todothing) as EditText).text.toString().length > 0)
    }

    fun checkData(): Boolean{
        return ((date?.length == "xxxx-xx-xx".length) and (time?.length == "xx:xx:xx".length))
    }

    fun showChooseDay(){
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int =  c.get(Calendar.MONTH)
        val day: Int =  c.get(Calendar.DAY_OF_MONTH)

        val newFragment: DatePickerDialog = DatePickerDialog(this@AddNewTodo,DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            date = year.toString() + "-" + twoNumFormatString.format(month+1) + "-" + twoNumFormatString.format(day)
        },year,month,day)

        newFragment.show()

    }

    fun showChooseTime(){
        val c: Calendar = Calendar.getInstance()
        val hour: Int = c.get(Calendar.HOUR_OF_DAY)
        val minute: Int = c.get(Calendar.MINUTE)
        val newFragment: TimePickerDialog = TimePickerDialog(this@AddNewTodo,TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            time = twoNumFormatString.format(hour) + ":" + twoNumFormatString.format(minute) + ":" + "00"
        },hour,minute,true)

        newFragment.show()
    }


}