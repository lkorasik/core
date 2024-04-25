import styles from "./ItemAddButton.module.css"

export interface Props {
    text: string
}

export function ItemAddButton(props: Props) {
    return <div className={styles.text_button}>{props.text}</div>
}