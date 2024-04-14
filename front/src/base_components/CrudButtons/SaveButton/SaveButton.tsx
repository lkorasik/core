import { Link } from "react-router-dom";
import styles from "./SaveButton.module.css";

interface Props {
    to: string;
    onClick?: () => void
}

export function SaveButton(props: Props) {
    return (
        <>
            <Link className={styles.linkOverride} to={props.to}>
                <button className={styles.saveButton} onClick={props.onClick} />
            </Link>
        </>
    )
}