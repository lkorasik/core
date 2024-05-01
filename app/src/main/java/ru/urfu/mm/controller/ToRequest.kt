package ru.urfu.mm.controller

interface ToRequest<T> {
    fun toRequest(): T
}