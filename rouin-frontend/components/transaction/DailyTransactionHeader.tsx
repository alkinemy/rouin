import React from "react";
import {format} from "date-fns"
import {DATE_FORMAT} from "../../lib/constants";


interface Props {
    date: Date
}


const DailyTransactionHeader: React.FC<Props> = ({date}) => {
    return (
        <div className="text-xs uppercase text-gray-400 border-b border-gray border-solid py-2 px-5 mb-2">
            {format(date, DATE_FORMAT)}
        </div>
    );
};


export default DailyTransactionHeader;
