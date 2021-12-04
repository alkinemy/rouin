import {Transaction} from "./transaction";


export type UserState = {
    userId: string | null;
    email: string | null;
    linkToken: string | null;
}

export type TransactionState = {
    transactions: Transaction[]
}
