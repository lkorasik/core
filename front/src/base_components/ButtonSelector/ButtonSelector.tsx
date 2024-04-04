import { useState } from "react"
import styles from "./ButtonSelector.module.css"
import { SelectButton } from "./SelectButton/SelectButton"

interface Props {
    /** Название группы радио-кнопок. Должно быть уникальным на странице */
    groupName: string
    buttons: Array<ButtonProps>
    checkedIndex: number
    onChange: ((label: string) => void)
}

interface ButtonProps {
    label: string
    onClick: (() => void)
}

export function ButtonSelector(props: Props) {
    const [select, setSelect] = useState(props.buttons[props.checkedIndex].label)

    const isRadioSelected = (label: string) => {
        return select === label
    }

    const button = (item: ButtonProps, index: number) => {
        return (
            <SelectButton
                groupName={props.groupName}
                label={item.label}
                onChange={(e) => {
                    setSelect(e)
                    props.onChange(e)}
                }
                number={index}
                inputClass={styles.input}
                checked={() => isRadioSelected(item.label)}
                key={index}
            />
        )
    }

    return (
        <div className={styles.wrapper}>
            {props.buttons.map((element, index) => button(element, index))}
        </div>
    )
}
