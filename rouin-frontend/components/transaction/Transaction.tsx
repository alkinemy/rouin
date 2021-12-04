import React from "react";
import {Transaction} from "../../types/transaction";


interface Props {
    transaction: Transaction
}

const Transaction: React.FC<Props> = ({transaction}) => {
    return (
        <li className="grid grid-cols-10 gap-4 justify-center items-center cursor-pointer px-4 py-2 rounded-lg hover:bg-gray-50">
            <div className="flex justify-center items-center">
                Icon holder
            </div>
            <div className="col-start-2 col-end-11 pl-8 border-l-2 border-solid border-gray">
                <h3 className="text-gray-900 font-medium text-md">{transaction.name}</h3>
                <p className="text-gray-600 mt-1 font-regular text-sm">
                    {transaction.category.name} | {transaction.account.name}
                </p>
            </div>
        </li>
    );
}

export default Transaction;