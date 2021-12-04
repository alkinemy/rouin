import React from "react";
import DailyTransactionHeader from "./DailyTransactionHeader";
import {Transaction} from "../../types/transaction";
import {default as DailyTransaction} from "./Transaction";


interface Props {
    date: Date,
    transactions: Transaction[];
}


const DailyTransactions: React.FC<Props> = ({date, transactions}) => {
    return (
        <>
            <DailyTransactionHeader date={date}/>
            {transactions.map(transaction => (
                <DailyTransaction key={transaction.transactionId} transaction={transaction}/>
            ))}
        </>
    )
}

export default DailyTransactions;