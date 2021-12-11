import {IllegalArgumentException} from "../exceptions";


class YearMonth {
    static START_OF_MONTH = 1;
    static END_OF_MONTH = 12;

    year: number;
    month: number;

    constructor(year: number, month: number) {
        if (year < 1) {
            throw new IllegalArgumentException("year should be bigger than or equal to 0");
        }
        if (month < 1) {
            throw new IllegalArgumentException("month should be bigger than or equal to 0");
        }
        if (month > 13) {
            throw new IllegalArgumentException("month should be less than or equal to 12");
        }
        this.year = year;
        this.month = month;
    }

    nextMonth(): YearMonth {
        if (this.month == YearMonth.END_OF_MONTH) {
            return new YearMonth(this.year + 1, YearMonth.START_OF_MONTH);
        }
        return new YearMonth(this.year, this.month + 1);
    }

    previousMonth(): YearMonth {
        if (this.month === YearMonth.START_OF_MONTH) {
            return new YearMonth(this.year - 1, YearMonth.END_OF_MONTH);
        }
        return new YearMonth(this.year, this.month - 1);
    }

    toDict() {
        return {
            year: this.year,
            month: this.month,
        };
    }
}


export default YearMonth;