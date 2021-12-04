import {TransactionState} from "../types/redux";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {Transaction} from "../types/transaction";


const initialState: TransactionState = {
    transactions: [],
};


const transaction = createSlice({
    name: "transaction",
    initialState,
    reducers: {
        setTransactions: (state, action: PayloadAction<Transaction[]>) => {
            state.transactions = action.payload;
            return state;
        },
    }
});


export const transactionActions = {...transaction.actions};


export default transaction;