import React, {useEffect} from "react";
import {useSelector} from "../../store";
import SearchBar from "../common/SearchBar";
import {format, parse, parseISO} from "date-fns";
import DailyTransactions from "./DailyTransactions";
import {Transaction} from "../../types/transaction";
import {DATE_FORMAT} from "../../lib/constants";
import {transactionApis} from "../../lib/api/transaction";
import {useDispatch} from "react-redux";
import {transactionActions} from "../../store/transaction";


const toFormatDate = (date: Date) => {
    return format(date, DATE_FORMAT)
}


const toDateObject = (date: string) => {
    return parse(date, DATE_FORMAT, new Date());
}


const MonthlyTransactions: React.FC = () => {
    const userId = useSelector(state => state.user.userId);
    const transactions = useSelector(state => state.transaction.transactions);
    const dispatch = useDispatch();
    const dateToTransactions: {
        [date: string]: Transaction[]
    } = {};
    transactions.forEach(transaction => {
        const previous = dateToTransactions[transaction.date] || [];
        dateToTransactions[transaction.date] = [...previous, transaction];
    });

    useEffect(() => {
        const now = new Date();
        transactionApis.getTransactions(userId, now.getFullYear(), now.getMonth() + 1)
            .then(({data: transactions}) => {
                const models = transactions.map(transaction => {
                    const model: Transaction = {
                        ...transaction,
                        date: format(parseISO(transaction.date), DATE_FORMAT),
                    }
                    return model;
                });
                dispatch(transactionActions.setTransactions(models))
            })
            .catch(e => console.log(e)); //TODO error handling
    }, []);

    return (
        <div className="rootContainer">
            <div className="relative">
                <SearchBar/>
                <ul className="rounded-md shadow-md bg-white absolute left-0 right-0 -bottom-18 mt-3 p-3">
                    {Object.entries(dateToTransactions).map(([date, transactions]) => (
                        <DailyTransactions key={date} date={toDateObject(date)} transactions={transactions}/>
                    ))}
                </ul>
            </div>
        </div>
    )
}

export default MonthlyTransactions;