package dev.kirillzhelt.paymentservice.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Token(
        @JsonProperty("token")
        val token: String,

        @JsonProperty("date_from")
        val dateFrom: String,

        @JsonProperty("date_to")
        val dateTo: String

)