package com.plcoding.graphqlmobileapp.domain

data class SimpleNode(
    val name: String,
    val space: SimpleSpace?,
    val data: SimpleData?
)
