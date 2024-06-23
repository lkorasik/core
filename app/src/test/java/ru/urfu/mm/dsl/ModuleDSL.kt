package ru.urfu.mm.dsl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestTemplate
import ru.urfu.mm.controller.Endpoints
import ru.urfu.mm.controller.modules.CreateModuleDTO
import ru.urfu.mm.controller.modules.ModuleDTO
import java.net.URI
import java.util.*


@Component
class ModuleDSL {
    @Autowired
    private lateinit var authorizationDSL: AuthorizationDSL

    fun createModule(address: String): UUID {
        val accessToken = authorizationDSL.registerAdministratorAccount(address)

        val name = UUID.randomUUID().toString().replace("-", "")
        val dto = CreateModuleDTO(name)

        val client = RestTemplate()
        val uri = URI.create(address + Endpoints.Module.create())
        val headers = org.springframework.http.HttpHeaders()
        headers.add("Authorization", "Bearer $accessToken")
        val entity = HttpEntity(dto, headers)
        client.exchange(uri, HttpMethod.POST, entity, Void::class.java)

        val defaultClient = RestClient
            .create()
            .mutate()
            .baseUrl(address)
            .defaultHeader("Authorization", "Bearer $accessToken")
            .build()
        val modules = defaultClient.get().uri(Endpoints.Module.all()).retrieve().body(Array<ModuleDTO>::class.java)!!

        return modules.first { it.name.equals(name) }.id
    }
}