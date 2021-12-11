import {useSelector} from "../../store";
import {useDispatch} from "react-redux";
import {transactionActions} from "../../store/transaction";
import YearMonth from "../../lib/model/YearMonth";
import React from "react";


const TransactionNavigationBar: React.FC = () => {
    const year = useSelector((state) => state.transaction.year);
    const month  = useSelector((state) => state.transaction.month);
    const yearMonth = new YearMonth(year, month);
    const dispatch = useDispatch();

    const toNextMonth = () => {
        dispatch(transactionActions.setYearMonth(yearMonth.nextMonth().toDict()));
    };
    const toPreviousMonth = () => {
        dispatch(transactionActions.setYearMonth(yearMonth.previousMonth().toDict()));
    };
    return (
        <div className="relative">
            <div className="flex justify-between uppercase text-lg border-b border-gray border-solid py-2 px-5 mb-2">
                <button className="justify-start" onClick={toPreviousMonth}>
                    &#8592;
                </button>
                <div className="justify-center">
                    {yearMonth.year}/{yearMonth.month}
                </div>
                <div className="justify-end" onClick={toNextMonth}>
                    &#8594;
                </div>
            </div>
        </div>
    );
}

export default TransactionNavigationBar;