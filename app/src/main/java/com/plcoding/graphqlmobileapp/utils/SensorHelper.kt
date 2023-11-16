package com.plcoding.graphqlmobileapp.utils

object SensorHelper {

    private val sensorsMap = mutableMapOf<String, Int>()

    init {
        sensorsMap.put( "6505c5094543cbb034793ef2" , 1)
        sensorsMap.put( "6505c702f490ba356202544f" , 2)
        sensorsMap.put( "65102bf52a6d1ce45e3792e3" , 1)
    }

    fun getSignalTypeNumber(): Int {
        return sensorsMap.map { it.value }.max()
    }

}