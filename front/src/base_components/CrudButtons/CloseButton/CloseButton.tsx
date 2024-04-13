import { Link } from "react-router-dom";
import styles from "./CloseButton.module.css";

interface Props {
    to: string
    onClick?: () => void
}

export function CloseButton(props: Props) {
    return (
        <>
            <Link className={styles.linkOverride} to={props.to}>
                <button className={styles.cancelButton} onClick={props.onClick}/>
            </Link>
        </>
    )
}