package com.android.zp.base.utils

import android.util.Base64
import com.android.zp.base.KLog
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class Base64Utils {
    companion object {
        /**
         * 对象类型需要实现Serializable接口
         */
        fun encodeBase64(obj: Any?): String {
            var target = ""
//            if (obj is String) {
//                val source: String = obj
//                target = String(Base64.encode(source.toByteArray(), Base64.NO_WRAP), Charsets.UTF_8)
//            }
            var baos: ByteArrayOutputStream? = null
            var oos: ObjectOutputStream? = null
            try {
                baos = ByteArrayOutputStream()
                oos = ObjectOutputStream(baos)
                oos.writeObject(obj)
                oos.flush()
                target = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                baos?.close()
                oos?.close()
            }
            return target
        }

        fun decodeBase64(str: String): Any {
            KLog.logI("decodeBase64 : $str")
            var obj: Any? = null
            val base64Array = Base64.decode(str.toByteArray(), Base64.NO_WRAP)
            var bais: ByteArrayInputStream? = null
            var ois: ObjectInputStream? = null
            try {
                bais = ByteArrayInputStream(base64Array)
                ois = ObjectInputStream(bais)
                obj = ois.readObject()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                bais?.close()
                ois?.close()
            }
            return obj!!
        }
    }


}