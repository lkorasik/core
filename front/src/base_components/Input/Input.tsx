import styles from "./Input.module.css"

interface Props {
    type?: string
    placeholder?: string
    isRequired?: boolean
    value?: string
    onChange?: React.ChangeEventHandler<HTMLInputElement> | undefined
}

export function Input(props: Props) {
    return (
        <input
            className={styles.input}
            type={props.type}
            placeholder={props.placeholder}
            required={props.isRequired}
            onChange={props.onChange}
            value={props.value}/>
    )
}