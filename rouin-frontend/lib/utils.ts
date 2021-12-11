import YearMonth from "./model/YearMonth";

export const currentDate = () => {
    const date = new Date();
    return {
        date: date,
        yearMonth: new YearMonth(date.getFullYear(), date.getMonth() + 1),
    }
}