package dev.kirillzhelt.paymentservice

const val SERVICE_REGISTRY = "https://voiteshenkolab3registerservice.azurewebsites.net/WebService.asmx/IsMethodExistsName"

val SERVICES = mapOf(
        "LibraryService" to "",
        "PlaneTicketService" to "",
        "RailwayTicketService" to "https://railwaytickets.azurewebsites.net/api/RailwayTickets/token")