import { ReactNode } from "react";
import styles from "./AddButton.module.css"
import { Link } from "react-router-dom";

type Props = {
    to?: string
    isEnabled?: boolean
    onClick?: (() => void)
}

export function AddButton({ isEnabled = true, onClick, to }: Props) {
    const renderButton = () => {
        return <button disabled={!isEnabled} className={styles.button} onClick={onClick} />
    }

    if (to) {
        return (
            <>
                <Link to={to}>
                    {renderButton()}
                </Link>
            </>
        )
    } else {
        return renderButton()
    }
}
