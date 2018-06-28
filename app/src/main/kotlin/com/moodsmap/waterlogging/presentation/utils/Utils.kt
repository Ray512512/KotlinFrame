package com.moodsmap.waterlogging.presentation.utils

import java.io.*

/**
 * Created by Ray on 2018/3/21.
 */
class Utils {
    companion object {
        fun saveObject(path: String, saveObject: Any) {
            var fos: FileOutputStream? = null
            var oos: ObjectOutputStream? = null
            val f = File(path)
            try {
                fos = FileOutputStream(f)
                oos = ObjectOutputStream(fos)
                oos.writeObject(saveObject)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    oos?.close()
                    fos?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        fun restoreObject(path: String): Any? {
            var fis: FileInputStream? = null
            var ois: ObjectInputStream? = null
            val f = File(path)
            if (!f.exists()) {
                return null
            }
            try {
                fis = FileInputStream(f)
                ois = ObjectInputStream(fis)
                return ois.readObject()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    ois?.close()
                    fis?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            return null
        }
    }
}