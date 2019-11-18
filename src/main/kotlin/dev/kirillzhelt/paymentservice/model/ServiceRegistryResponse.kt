package dev.kirillzhelt.paymentservice.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ServiceRegistryResponse(

        @JsonProperty("Message")
        val methodExists: Boolean
)