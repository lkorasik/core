import { useState } from "react";
import styles from "./Cell.module.css";

export interface Props {
    x: number
    y: number
    onActivate?: (x: number, y: number) => void
    onDeactivate?: (x: number, y: number) => void
}

export function Cell(props: Props) {
    const [isActive, setActive] = useState(false);

    const toggleActive = () => {
        const newState = !isActive;
        if (newState) {
            props?.onActivate?.(props.x, props.y)
        } else {
            props?.onDeactivate?.(props.x, props.y)
        }
        setActive(newState)
    }

    return (
        <td 
            className={styles.cell} 
            onClick={toggleActive}>
                {isActive && "X"}
                {!isActive && "O"}
        </td>
    )
}