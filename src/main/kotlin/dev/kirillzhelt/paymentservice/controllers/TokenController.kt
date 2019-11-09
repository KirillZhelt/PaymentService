package dev.kirillzhelt.paymentservice.controllers

import dev.kirillzhelt.paymentservice.model.Greeting
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong


@RestController
class TokenController {
    val counter = AtomicLong()

    @GetMapping("/greeting/{id}")
    fun greeting(@PathVariable id: Int, @RequestParam(value = "name", defaultValue = "World") name: String) =
            Greeting(counter.incrementAndGet(), "Hello, $name $id")

}