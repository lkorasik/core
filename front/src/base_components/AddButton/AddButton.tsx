import { ReactNode } from "react";
import styles from "./AddButton.module.css"

type Props = {
    children?: ReactNode | ReactNode[];
    isEnabled?: boolean
    onClick?: (() => void)
}

export function AddButton({ isEnabled = true , children, onClick }: Props) {
    return (
        <button disabled={!isEnabled} className={styles.button} onClick={onClick} type="button">
        </button>
    )
}
