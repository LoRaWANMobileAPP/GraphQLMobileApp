package com.plcoding.graphqlmobileapp.utils

import java.io.File
import java.io.FileInputStream
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Properties


object Helper {
    private const val TAG = "Helper"
    fun getConfigValue(name: String): String? {
////    fun main() {
//        val file = File("/res/config.properties")
//
//        val prop = Properties()
//        FileInputStream(file).use { prop.load(it) }
//
//        // Print all properties
//        val a = prop.stringPropertyNames()
//            .associateWith {prop.getProperty(it)}
//            .firstOrNull { println(it) }
        return null
    }


    private const val dimensionFourTimeFormat = "yyyy-MM-dd HH:mm:ss.SSS"

    fun stringToTimestamp(dateString: String): Timestamp {
        val dateFormat = SimpleDateFormat(dimensionFourTimeFormat)
        val parsedDate = dateFormat.parse(dateString.replace("T", " ").replace("Z",""))

        return Timestamp(parsedDate.time)
    }
}
