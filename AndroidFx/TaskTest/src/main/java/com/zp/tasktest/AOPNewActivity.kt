package com.zp.tasktest

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import com.android.zp.base.KLog
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class AOPNewActivity : Activity(),DBOperation {

    var db:DBOperation ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task);
        db = Proxy.newProxyInstance(DBOperation::class.java.classLoader, arrayOf<Class<*>>(DBOperation::class.java), DBHander(this)) as DBOperation
        findViewById<Button>(R.id.jump).setOnClickListener {
            jump()
        }
    }

    internal class DBHander(private val db: DBOperation?) : InvocationHandler {

        override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
            if (db != null) {
                KLog.logI("操作数据库之前，开始备份……")
                db.save()
                KLog.logI("数据备份完成，等待操作")
                return method?.invoke(db,*args.orEmpty())
            }
            return null
        }
    }

    fun jump() {
        db?.insert()
        db?.update()
        db?.delete()
    }

    override fun insert() {
        KLog.logI("insert")
    }

    override fun delete() {
        KLog.logI("delete")
    }

    override fun update() {
        KLog.logI("update")
    }

    override fun save() {
        KLog.logI("save")
    }
}