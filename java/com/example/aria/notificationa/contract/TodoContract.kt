package com.example.aria.notificationa.contract

import android.provider.BaseColumns

class TodoContract {
    class TodoContractEntry : BaseColumns {
        companion object {
            val TABLE_NAME: String = "Todo_List"
            val TODO_THING: String = "Todo_Thing"
            val TODO_TIME:String = "Todo_Time"
            val TODO_ADD_TIMESTAMP: String = "Todo_Add_Time"
        }

    }
}