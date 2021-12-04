import React from "react";
import {Transaction} from "../../types/transaction";
import Amount from "../common/Amount";


interface Props {
    transaction: Transaction
}

const DailyTransaction: React.FC<Props> = ({transaction}) => {
    return (
        <li className="grid grid-cols-10 gap-4 justify-center items-center cursor-pointer px-4 py-2 rounded-lg hover:bg-gray-50">
            <h2 className="flex justify-center items-center">
                icon holder
            </h2>
            <div className="col-start-2 col-end-10 pl-8 border-l-2 border-solid border-gray">
                <h3 className="text-gray-900 font-medium text-md">{transaction.name}</h3>
                <p className="text-gray-600 mt-1 font-regular text-sm">
                    {transaction.category.name} | {transaction.account.name}
                </p>
            </div>
            <div className="flex justify-end items-center">
                <Amount amount={transaction.amount}/>
            </div>
        </li>
    );
}

export default DailyTransaction;