import styles from "./Input.module.css"

interface Prop {
    type: string
    placeholder: string
    isRequired: boolean
    onChange: React.ChangeEventHandler<HTMLInputElement> | undefined
}

export function Input(props: Prop) {
    return (
        <input
            className={styles.input}
            type={props.type}
            placeholder={props.placeholder}
            required={props.isRequired}
            onChange={props.onChange}/>
    )
}