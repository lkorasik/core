import { Link } from "react-router-dom";
import styles from "./EditButton.module.css";

interface Props {
    to: string;
    onClick?: () => void
}

export function EditButton(props: Props) {
    return (
        <>
            <Link className={styles.linkOverride} to={props.to}>
                <button className={styles.editButton} onClick={props.onClick} />
            </Link>
        </>
    )
}