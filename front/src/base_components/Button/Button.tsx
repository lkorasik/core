import { ReactNode } from "react";
import styles from "./Button.module.css"

type Props = {
    id?: string
    className?: string
    children?: ReactNode | ReactNode[];
    isEnabled?: boolean
    onClick?: (() => void)
}

export function Button({ id, className, isEnabled = true , children, onClick }: Props) {
    return (
        <button id={id} disabled={!isEnabled} className={`${className} ${styles.button}`} onClick={onClick} type="button">
            <span className={styles.innerText}>
                {children}
            </span>
        </button>
    )
}