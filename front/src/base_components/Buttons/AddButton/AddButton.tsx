import styles from "./AddButton.module.css"
import { useNavigate } from "react-router-dom";

type Props = {
    to?: string
    isEnabled?: boolean
    onClick?: (() => void)
}

export function AddButton({ isEnabled = true, onClick, to }: Props) {
    const navigate = useNavigate()
    
    return (
        <>
            <div className={styles.button_container}>
                <button disabled={!isEnabled} className={styles.button} onClick={() => {
                    onClick?.()
                    navigate(to ? to : "")
                }} />
            </div>
        </>
    )
}
