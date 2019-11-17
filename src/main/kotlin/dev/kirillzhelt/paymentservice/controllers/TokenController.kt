package dev.kirillzhelt.paymentservice.controllers

import dev.kirillzhelt.paymentservice.model.Greeting
import dev.kirillzhelt.paymentservice.model.PaymentInfo
import dev.kirillzhelt.paymentservice.model.Response
import dev.kirillzhelt.paymentservice.model.Token
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import javax.validation.Valid


@RestController
class TokenController {
    val counter = AtomicLong()

    @GetMapping("/greeting/{id}")
    fun greeting(@PathVariable id: Int, @RequestParam(value = "name", defaultValue = "World") name: String) =
            Greeting(counter.incrementAndGet(), "Hello, $name $id")

    // curl --request POST --url "http://localhost:8080/token/service/method123" --header "content-type: application/json;charset=utf-8" --data "{\"date_from\":\"12-12-2000\", \"date_to\":\"12-12-2019\"}"
    // curl --request POST --url "http://cryptic-beach-05943.herokuapp.com/token/service/method123" --header "content-type: application/json;charset=utf-8" --data "{\"date_from\":\"12-12-2000\", \"date_to\":\"12-12-2019\"}"

    @PostMapping("/token/{service}/{method}")
    fun token(@PathVariable(value = "service") serviceName: String, @PathVariable(value = "method") methodName: String,
              @Valid @RequestBody paymentInfo: PaymentInfo): Response {

        // 1. Проверить наличие данного метода и сервиса в регистре сервисов, если такого сервиса нет вернуть бед реквест
        // 2. Сервису отправить Token
        // 3. Клиенту отправить стрингу с токеном

        if (checkMethod(serviceName, methodName)) {
            val tokenString = generateToken()

            val tokenForService = Token(tokenString, paymentInfo, methodName)

            sendToService(serviceName, tokenForService)

            // token for client
            return Response(token = tokenString, statusCode = 201)
        } else {
            return Response(statusCode = 404, statusMessage = "Invalid method")
        }

    }

    private fun checkMethod(serviceName: String, methodName: String): Boolean {
        // TODO: check method in the registry

        return when ((0..1).random()) {
            0 -> false
            else -> true
        }
    }

    private fun generateToken(): String {
        return UUID.randomUUID().toString()

    }

    private fun sendToService(serviceName: String, token: Token) {
        // TODO: send token to service

    }

}