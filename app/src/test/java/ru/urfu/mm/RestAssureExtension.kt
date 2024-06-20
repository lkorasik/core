package ru.urfu.mm

import io.restassured.response.ExtractableResponse
import io.restassured.response.ResponseOptions
import io.restassured.specification.RequestSpecification

/**
 * Набор расширений для Rest Assured
 */
object RestAssureExtension {
    fun RequestSpecification.whenever(): RequestSpecification = this.`when`()
    fun <R : ResponseOptions<R>?, T> ExtractableResponse<R>.cast(type: Class<T>): T = this.`as`(type)
}