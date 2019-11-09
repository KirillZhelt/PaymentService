package dev.kirillzhelt.paymentservice.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Token(
        @JsonProperty("token")
        val token: String,

        @JsonProperty("payment_info")
        val paymentInfo: PaymentInfo,

        @JsonProperty("method_name")
        val methodName: String
)