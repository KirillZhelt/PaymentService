package dev.kirillzhelt.paymentservice

const val SERVICE_REGISTRY = "https://voiteshenkolab3registerservice.azurewebsites.net/WebService.asmx/IsMethodExistsName"

val SERVICES = mapOf(
        "LibraryService" to "https://voiteshenko-library-service.azurewebsites.net/LibraryService.asmx/AddToken",
        "PlaneTicketService" to "http://voiteshenko-lab3-plane-ticket-service.azurewebsites.net/PlaneTicketService.svc/setToken",
        "RailwayTickets" to "https://railwaytickets.azurewebsites.net/api/RailwayTickets/token")