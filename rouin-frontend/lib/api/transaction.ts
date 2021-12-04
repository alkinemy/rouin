import axios from "./index";
import {Transaction} from "../../types/transaction";


const getTransactions = (userId: string, year: number, month: number) => axios.get<Transaction[]>(`/api/v1/ledgers/${userId}/transactions?year=${year}&month=${month}`);


export const transactionApis = {getTransactions};