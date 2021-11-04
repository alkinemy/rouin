package al.rouin.account

import com.plaid.client.model.AccountSubtype


interface AccountType {
    val name: String

    companion object {
        fun toAccountType(plaidType: com.plaid.client.model.AccountType) =
            SupportedAccountType.find(plaidType) ?: UnsupportedAccountType(name = plaidType.name)
    }
}


enum class SupportedAccountType(
    val plaidType: com.plaid.client.model.AccountType
) : AccountType {
    DEPOSITORY(com.plaid.client.model.AccountType.DEPOSITORY),
    CREDIT(com.plaid.client.model.AccountType.CREDIT);

    companion object {
        private val plaidTypeToAccountType = values().associateBy { it.plaidType }
        fun find(plaidType: com.plaid.client.model.AccountType): AccountType? = plaidTypeToAccountType[plaidType]
    }
}


data class UnsupportedAccountType(
    override val name: String
) : AccountType


interface AccountSubType {
    val name: String

    companion object {
        fun toAccountSubType(plaidType: AccountSubtype): AccountSubType =
            SupportedAccountSubType.find(plaidType) ?: UnsupportedAccountSubType(name = plaidType.name)
    }
}


enum class SupportedAccountSubType(
    val plaidType: AccountSubtype
) : AccountSubType {
    CHECKING(AccountSubtype.CHECKING),
    SAVING(AccountSubtype.SAVINGS),
    CREDIT_CARD(AccountSubtype.CREDIT_CARD);

    companion object {
        private val plaidTypeToAccountSubType = values().associateBy { it.plaidType }
        fun find(plaidType: AccountSubtype): AccountSubType? = plaidTypeToAccountSubType[plaidType]
    }
}


data class UnsupportedAccountSubType(
    override val name: String
) : AccountSubType
