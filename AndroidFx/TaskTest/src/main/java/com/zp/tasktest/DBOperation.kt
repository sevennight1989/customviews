package com.zp.tasktest

interface DBOperation{
    fun insert()

    fun delete()

    fun update()

    // 数据备份
    fun save()
}