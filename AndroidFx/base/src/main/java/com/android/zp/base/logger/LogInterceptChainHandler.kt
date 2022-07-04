package com.android.zp.base.logger

class LogInterceptChainHandler {

    private var interceptFirst: LogInterceptChain? = null

    fun add(interceptChain: LogInterceptChain?) {
        if (interceptFirst == null) {
            interceptFirst = interceptChain;
            return
        }
        var node: LogInterceptChain? = interceptFirst
        while (true) {
            if (node?.next == null) {
                node?.next = interceptChain;
                break
            }
            node = node.next
        }
    }

    fun intercept(priority: Int, tag: String, logMsg: String?) {
        interceptFirst?.intercept(priority, tag, logMsg)
    }
}