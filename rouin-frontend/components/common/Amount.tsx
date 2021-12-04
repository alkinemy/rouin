import React from "react";
import styles from "./Amount.module.css"


interface Props {
    amount: number
}

const Amount: React.FC<Props> = ({amount}) => {
    return (
        <>
            {amount > 0 ? (
                <div className={styles.positiveTransactionAmount}>
                    +{amount}
                </div>
            ) : (
                <div className={styles.negativeTransactionAmount}>
                    {amount}
                </div>
            )}
        </>
    );
}


export default Amount;