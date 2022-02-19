import {Transaction} from "./transaction";


export type UserState = {
    userId: string | null;
    email: string | null;
    token: string | null;
}

export type TransactionState = {
    year: number;
    month: number;
    transactions: Transaction[]
}
