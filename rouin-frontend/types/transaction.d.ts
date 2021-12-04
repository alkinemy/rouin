export type Transaction = {
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


export type Account = {
    accountId: string;
    name: string;
    alias: string;
    officialName: string | null,
    type: string;
    subType: string;
}


export type Category = {
    categoryId: string;
    userId: string;
    name: string;
}