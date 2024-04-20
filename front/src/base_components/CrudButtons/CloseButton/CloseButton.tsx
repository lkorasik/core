import { useNavigate } from "react-router-dom";
import styles from "./CloseButton.module.css";

export function CloseButton() {
    const navigate = useNavigate();

    return <button className={styles.cancelButton} onClick={() => navigate(-1)}/>
}