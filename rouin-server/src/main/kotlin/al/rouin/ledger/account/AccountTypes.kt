package al.rouin.ledger.account

import al.rouin.common.isNotNull
import com.plaid.client.model.AccountSubtype


enum class AccountType(
    val plaidType: com.plaid.client.model.AccountType? = null
) {
    DEPOSITORY(com.plaid.client.model.AccountType.DEPOSITORY),
    CREDIT(com.plaid.client.model.AccountType.CREDIT),
    UNSUPPORTED;

    companion object {
        private val plaidTypeToType = values().filter { it.plaidType.isNotNull() }.associateBy { it.plaidType }
        private val nameToType = values().associateBy { it.name }

        fun find(plaidType: com.plaid.client.model.AccountType): AccountType? = plaidTypeToType[plaidType]
        fun find(name: String): AccountType? = nameToType[name]
    }
}


enum class AccountSubType(
    val plaidType: AccountSubtype? = null
) {
    CHECKING(AccountSubtype.CHECKING),
    SAVING(AccountSubtype.SAVINGS),
    CREDIT_CARD(AccountSubtype.CREDIT_CARD),
    UNSUPPORTED;

    companion object {
        private val plaidTypeToType = values().filter { it.plaidType.isNotNull() }.associateBy { it.plaidType }
        private val nameToType = values().associateBy { it.name }

        fun find(plaidType: AccountSubtype): AccountSubType? = plaidTypeToType[plaidType]
        fun find(name: String): AccountSubType? = nameToType[name]
    }
}