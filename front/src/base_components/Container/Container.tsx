import { ReactNode } from "react";
import styles from "./Container.module.css";

interface Props {
    children: ReactNode | ReactNode[]
}

export function Container(props: Props) {
    return (
        <>
            <div className={styles.outer_container}>
                <div className={styles.inner_container}>
                    {props.children}
                </div>
            </div>
        </>
    )
}