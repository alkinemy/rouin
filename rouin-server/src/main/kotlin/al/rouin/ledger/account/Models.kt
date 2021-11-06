package al.rouin.ledger.account

import al.rouin.external.ReferenceId
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING
import com.fasterxml.jackson.annotation.JsonValue
import java.util.*


data class Account(
    val accountId: AccountId,
    val referenceId: ReferenceId,
    val name: String,
    val aliasName: String,
    val officialName: String?,
    val accountType: AccountType,
    val accountSubType: AccountSubType?,
)


data class AccountId @JsonCreator(mode = DELEGATING) constructor(
    val id: String
) {
    companion object {
        fun newId() = AccountId(id = UUID.randomUUID().toString())
    }

    @JsonValue
    override fun toString() = id
}

data class AccountReference(
    val referenceId: ReferenceId,
    val name: String,
    val officialName: String?,
    val accountType: AccountType,
    val accountSubType: AccountSubType?,
)