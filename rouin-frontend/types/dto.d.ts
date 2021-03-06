import {Account, Category} from "./transaction";


export type UserDto = {
    userId: string;
    email: string;
}


export type TokenDto = {
    token: string;
}


export type TransactionDto = {
    transactionId: string;
    userId: string;
    account: Account;
    category: Category;
    name: string;
    amount: number;
    date: string;
    currency: string;
    description: string;
}


export type AccountDto = {
    accountId: string;
    name: string;
    alias: string;
    officialName: string | null,
    type: string;
    subType: string;
}


export type CategoryDto = {
    categoryId: string;
    userId: string;
    name: string;
}