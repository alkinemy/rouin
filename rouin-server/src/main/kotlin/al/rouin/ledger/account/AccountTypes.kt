package al.rouin.ledger.account

import al.rouin.common.isNotNull
import com.plaid.client.model.AccountSubtype


enum class AccountType(
    private val plaidType: com.plaid.client.model.AccountType? = null
) {
    DEPOSITORY(com.plaid.client.model.AccountType.DEPOSITORY),
    CREDIT(com.plaid.client.model.AccountType.CREDIT),
    UNSUPPORTED;

    companion object {
        private val plaidTypeToType = values().filter { it.plaidType.isNotNull() }.associateBy { it.plaidType }

        fun find(plaidType: com.plaid.client.model.AccountType): AccountType? = plaidTypeToType[plaidType]
    }
}


enum class AccountSubType(
    private val plaidType: AccountSubtype? = null
) {
    CHECKING(AccountSubtype.CHECKING),
    SAVING(AccountSubtype.SAVINGS),
    CREDIT_CARD(AccountSubtype.CREDIT_CARD),
    UNSUPPORTED;

    companion object {
        private val plaidTypeToType = values().filter { it.plaidType.isNotNull() }.associateBy { it.plaidType }

        fun find(plaidType: AccountSubtype): AccountSubType? = plaidTypeToType[plaidType]
    }
}