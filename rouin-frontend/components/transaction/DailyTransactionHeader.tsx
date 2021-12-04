import React from "react";
import {format} from "date-fns"
import {DATE_FORMAT} from "../../lib/constants";
import {Transaction} from "../../types/transaction";
import Amount from "../common/Amount";


interface Props {
    date: Date;
    transactions: Transaction[];
}


const DailyTransactionHeader: React.FC<Props> = ({date, transactions}) => {
    const totalAmount = transactions.reduce((partialTransactionAmountSum, transaction) => partialTransactionAmountSum + transaction.amount, 0);
    return (
        <div className="flex justify-between uppercase border-b border-gray border-solid py-2 px-5 mb-2">
            <div className="flex justify-start text-gray-400">
                {format(date, DATE_FORMAT)}
            </div>
            <div className="flex justify-end">
                <Amount amount={totalAmount}/>
            </div>
        </div>
    );
};


export default DailyTransactionHeader;
