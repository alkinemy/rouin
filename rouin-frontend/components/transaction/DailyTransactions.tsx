import React from "react";
import DailyTransactionHeader from "./DailyTransactionHeader";
import {Transaction} from "../../types/transaction";
import DailyTransaction from "./DailyTransaction";


interface Props {
    date: Date,
    transactions: Transaction[];
}


const DailyTransactions: React.FC<Props> = ({date, transactions}) => {
    return (
        <>
            <DailyTransactionHeader date={date} transactions={transactions}/>
            {transactions.map(transaction => (
                <DailyTransaction key={transaction.transactionId} transaction={transaction}/>
            ))}
            <div className="mb-8"/>
        </>
    )
}

export default DailyTransactions;