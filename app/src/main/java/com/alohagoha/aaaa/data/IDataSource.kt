package com.alohagoha.aaaa.data

interface IDataSource<T> {
    fun getAllData(): List<T>
}