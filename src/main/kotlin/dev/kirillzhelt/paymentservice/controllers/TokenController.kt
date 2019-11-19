package dev.kirillzhelt.paymentservice.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.kirillzhelt.paymentservice.SERVICES
import dev.kirillzhelt.paymentservice.SERVICE_REGISTRY
import dev.kirillzhelt.paymentservice.model.*
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.web.bind.annotation.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import javax.validation.Valid


@RestController
class TokenController {

    private val client = OkHttpClient()
    private val mapper = jacksonObjectMapper()

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

            val tokenForService = Token(tokenString, paymentInfo.dateFrom, paymentInfo.dateTo)

            sendToService(serviceName, methodName, tokenForService)

            // token for client
            return Response(token = tokenString, statusCode = 201)
        } else {
            return Response(statusCode = 404, statusMessage = "Invalid method")
        }

    }

    // voiteshenkolab3registerservice.azurewebsites.net/WebService.asmx/IsMethodExistsName?serviceName=Money&methodName=Add

    private fun checkMethod(serviceName: String, methodName: String): Boolean {
        val urlString = "$SERVICE_REGISTRY?serviceName=$serviceName&methodName=$methodName"

        with (URL(urlString).openConnection() as HttpURLConnection) {
            requestMethod = "GET"

            val jsonResponseString = inputStream.bufferedReader().readLine()

            val serviceRegistryResponse: ServiceRegistryResponse = mapper.readValue(jsonResponseString)

            return serviceRegistryResponse.methodExists
        }
    }

    private fun generateToken(): String {
        return UUID.randomUUID().toString()
    }

    // curl --request POST --url "http://voiteshenko-lab3-plane-ticket-service.azurewebsites.net/PlaneTicketService.svc/setToken/methodName" --header "content-type: application/json;charset=utf-8" --data "{\"tokenValue\":\"token1234\", \"date_from\":\"12-12-2000\",\"date_to\":\"12-12-2000\"}"

    private fun sendToService(serviceName: String, methodName: String, token: Token) {
        val serviceUrl = "${SERVICES.getValue(serviceName)}/$methodName"

        val tokenJsonString = mapper.writeValueAsString(token)

        val requestBody = tokenJsonString.toRequestBody(JSON)

        val request = Request.Builder()
                .url(serviceUrl)
                .post(requestBody)
                .build()

        client.newCall(request).execute()
//        println(client.newCall(request).execute().body?.string())
    }

    companion object {
        private val JSON = "application/json; charset=utf-8".toMediaType()
    }
}
