import { Link } from "react-router-dom";
import styles from "./DeleteButton.module.css";

interface Props {
    to: string
    onClick?: () => void
}

export function DeleteButton(props: Props) {
    return (
        <>
            <Link className={styles.linkOverride} to={props.to}>
                <button className={styles.deleteButton} onClick={props.onClick}/>
            </Link>
        </>
    )
}