package org.mk.playlist.models.data_stores

interface DataStore<T> {
    fun findAll(): List<T>
    fun create(obj: T)
    fun update(obj: T)
    fun add(obj: T)
    fun delete(obj: T)
}