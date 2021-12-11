import {Transaction} from "./transaction";
import YearMonth from "../lib/model/YearMonth";


export type UserState = {
    userId: string | null;
    email: string | null;
    linkToken: string | null;
}

export type TransactionState = {
    year: number;
    month: number;
    transactions: Transaction[]
}
