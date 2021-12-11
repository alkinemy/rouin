import {TransactionState} from "../types/redux";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {Transaction} from "../types/transaction";
import {currentDate} from "../lib/utils";


const initialState: TransactionState = (() => {
    const current = currentDate();
    return {
        year: current.yearMonth.year,
        month: current.yearMonth.month,
        transactions: [],
    }
})();


const transaction = createSlice({
    name: "transaction",
    initialState,
    reducers: {
        setTransactions: (state, action: PayloadAction<Transaction[]>) => {
            state.transactions = action.payload;
            return state;
        },
        setYearMonth: (state, action: PayloadAction<{ year: number; month: number }>) => {
            state.year = action.payload.year;
            state.month = action.payload.month;
            return state;
        },
    }
});


export const transactionActions = {...transaction.actions};


export default transaction;