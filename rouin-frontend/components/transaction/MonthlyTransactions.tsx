import React, {useEffect} from "react";
import {useSelector} from "../../store";
import TransactionSearchBar from "./TransactionSearchBar";
import {format, parse, parseISO} from "date-fns";
import DailyTransactions from "./DailyTransactions";
import {Transaction} from "../../types/transaction";
import {DATE_FORMAT} from "../../lib/constants";
import {transactionApis} from "../../lib/api/transaction";
import {useDispatch} from "react-redux";
import {transactionActions} from "../../store/transaction";
import TransactionNavigationBar from "./TransactionNavigationBar";
import YearMonth from "../../lib/model/YearMonth";


const toDateObject = (date: string) => {
    return parse(date, DATE_FORMAT, new Date());
}


const MonthlyTransactions: React.FC = () => {
    const userId = useSelector(state => state.user.userId);
    const year = useSelector((state) => state.transaction.year);
    const month = useSelector((state) => state.transaction.month);
    const transactions = useSelector(state => state.transaction.transactions);
    const dispatch = useDispatch();
    const yearMonth = new YearMonth(year, month);

    const dateToTransactions: {
        [date: string]: Transaction[]
    } = {};
    transactions.forEach(transaction => {
        const previous = dateToTransactions[transaction.date] || [];
        dateToTransactions[transaction.date] = [...previous, transaction];
    });

    useEffect(() => {
        transactionApis.getTransactions(userId, yearMonth)
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
    }, [year, month]);

    return (
        <div className="rootContainer">
            <div className="relative">
                <TransactionNavigationBar/>
                <TransactionSearchBar/>
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