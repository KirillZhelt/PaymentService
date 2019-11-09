package dev.kirillzhelt.paymentservice.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Response(

        @JsonProperty("token")
        val token: String? = null,

        @JsonProperty("status_code")
        val statusCode: Int? = null,

        @JsonProperty("status_message")
        val statusMessage: String? = null
)