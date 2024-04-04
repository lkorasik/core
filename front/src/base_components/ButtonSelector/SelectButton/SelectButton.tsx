import styles from "./SelectButton.module.css"

interface Props {
    groupName: string
    label: string
    number: number
    inputClass: string
    checked: (() => boolean)
    onChange: ((label: string) => void)
}

export function SelectButton(props: Props) {
    const buttonId = `id-${props.groupName}-option-${props.number}`
    const buttonClass = `class-${props.groupName}-option-${props.number}`
    const spanId = `span-for-${buttonId}`

    const style = `
        #${buttonId}:checked:checked ~ .${buttonClass} span {
            color: #1E4391;
        }
        #${buttonId}:checked:checked ~ .${buttonClass} {
            border-color: #1E4391;
        }
        .${buttonClass} #${spanId} {
            font-size: 20px;
            color: rgba(34, 34, 34, 0.2);
        }`

    return (
        <>
            <style>{style}</style>
            <input
                type="radio"
                className={props.inputClass}
                name={props.groupName}
                id={buttonId}
                checked={props.checked()}
                onChange={() => props.onChange(props.label)}
            />
            <label
                htmlFor={buttonId}
                className={`${styles.label} ${buttonClass}`}
            >
                <span id={spanId}>{props.label}</span>
            </label>
        </>
    )
}