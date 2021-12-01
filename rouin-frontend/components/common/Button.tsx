import React from "react";
import styles from "./Button.module.css";


interface Props extends React.ButtonHTMLAttributes<HTMLButtonElement> {
    children: React.ReactNode;
}


const Button: React.FC<Props> = ({children, ...props}) => {
    return (
        <button className={styles.buttonDefault} {...props}>
            {children}
        </button>
    );
}

export default Button;