package ru.urfu.mm.dsl

import ru.urfu.mm.persistance.entity.RegistrationToken
import java.util.UUID

object RegistrationTokenFactory {
    fun build(uuid: UUID = UUID.randomUUID()): RegistrationToken {
        return RegistrationToken(uuid)
    }
}