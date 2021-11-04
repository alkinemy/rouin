package al.rouin.ledger.account

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING
import com.fasterxml.jackson.annotation.JsonValue
import com.plaid.client.model.AccountSubtype


interface AccountType {
    val name: String

    companion object {
        fun toAccountType(plaidType: com.plaid.client.model.AccountType) =
            SupportedAccountType.find(plaidType) ?: UnsupportedAccountType(name = plaidType.name)

        fun toAccountType(name: String) =
            SupportedAccountType.find(name) ?: UnsupportedAccountType(name = name)
    }
}


enum class SupportedAccountType(
    val plaidType: com.plaid.client.model.AccountType
) : AccountType {
    DEPOSITORY(com.plaid.client.model.AccountType.DEPOSITORY),
    CREDIT(com.plaid.client.model.AccountType.CREDIT);

    companion object {
        private val plaidTypeToType = values().associateBy { it.plaidType }
        private val nameToType = values().associateBy { it.name }

        fun find(plaidType: com.plaid.client.model.AccountType): AccountType? = plaidTypeToType[plaidType]
        fun find(name: String): AccountType? = nameToType[name]
    }
}


data class UnsupportedAccountType @JsonCreator(mode = DELEGATING) constructor(
    override val name: String
) : AccountType {
    @JsonValue
    override fun toString() = name
}


interface AccountSubType {
    val name: String

    companion object {
        fun toAccountSubType(plaidType: AccountSubtype): AccountSubType =
            SupportedAccountSubType.find(plaidType) ?: UnsupportedAccountSubType(name = plaidType.name)

        fun toAccountSubType(name: String): AccountSubType =
            SupportedAccountSubType.find(name) ?: UnsupportedAccountSubType(name = name)
    }
}


enum class SupportedAccountSubType(
    val plaidType: AccountSubtype
) : AccountSubType {
    CHECKING(AccountSubtype.CHECKING),
    SAVING(AccountSubtype.SAVINGS),
    CREDIT_CARD(AccountSubtype.CREDIT_CARD);

    companion object {
        private val plaidTypeToType = values().associateBy { it.plaidType }
        private val nameToType = values().associateBy { it.name }

        fun find(plaidType: AccountSubtype): AccountSubType? = plaidTypeToType[plaidType]
        fun find(name: String): AccountSubType? = nameToType[name]
    }
}


data class UnsupportedAccountSubType @JsonCreator(mode = DELEGATING) constructor(
    override val name: String
) : AccountSubType {
    @JsonValue
    override fun toString() = name
}
