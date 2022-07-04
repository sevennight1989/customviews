package com.android.zp.base.logger

abstract class LogInterceptChain {
    var next:LogInterceptChain? = null

    open fun intercept(priority:Int,tag:String,logMsg:String?){
        next?.intercept(priority,tag,logMsg)
    }
}