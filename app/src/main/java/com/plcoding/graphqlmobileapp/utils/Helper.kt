package com.plcoding.graphqlmobileapp.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat

object Helper {
    private const val dimensionFourTimeFormat = "yyyy-MM-dd HH:mm:ss.SSS"

    fun stringToTimestamp(dateString: String): Timestamp {
        val dateFormat = SimpleDateFormat(dimensionFourTimeFormat)
        val parsedDate = dateFormat.parse(dateString.replace("T", " ").replace("Z",""))

        return Timestamp(parsedDate.time)
    }

    fun eliminateMillisecond(timestamp: Timestamp): String {
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy, HH:mm:ss")
        return simpleDateFormat.format(timestamp)
    }
}

