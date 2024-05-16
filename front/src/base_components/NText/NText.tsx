import styles from "./NText.module.css"

export interface Props {
    children?: string
    isRequired?: boolean
}

export function NText(props: Props) {
    const renderMark = () => {
        if (props.isRequired) {
            return <span className={styles.mark}>*</span>
        }
    }

    return (
        <span className={styles.text}>{props.children} {renderMark()}</span>
    )
}