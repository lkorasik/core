import { ReactNode } from "react";
import { Title } from "../Title/Title";
import styles from "./Toolbar.module.css";

interface Props {
    title: string
    children?: ReactNode | ReactNode[]
}

export function Toolbar(props: Props) {
    return (
        <>
            <div className={styles.toolbar}>
                <Title>{props.title}</Title>
                <div className={styles.actions}>
                    {props.children}
                </div>
            </div>
        </>
    )
}