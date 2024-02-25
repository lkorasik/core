import React from "react";
import styles from "./TextLoadingPlaceholder.module.css";

interface Props {
    className: string;
}

export const TextLoadingPlaceholder = (props: Props) => {
    return (
        <div className={props.className}>
            <div className={styles.placeholder}></div>
        </div>
    );
}
