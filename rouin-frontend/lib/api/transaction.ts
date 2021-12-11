import axios from "./index";
import {Transaction} from "../../types/transaction";
import YearMonth from "../model/YearMonth";


const getTransactions = (userId: string, yearMonth: YearMonth) => axios.get<Transaction[]>(`/api/v1/ledgers/${userId}/transactions?year=${yearMonth.year}&month=${yearMonth.month}`);


export const transactionApis = {getTransactions};