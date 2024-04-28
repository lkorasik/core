import { FullModuleDto } from "../../apis/api/modules/FullModuleDto"
import { CheckBox } from "../NewStudyPlan/NewStudyPlan";
import styles from "./ModuleTable.module.css";

export interface Props {
    module: FullModuleDto
    shift: number
    matrix: CheckBox[][]
    setMatrix: React.Dispatch<React.SetStateAction<CheckBox[][]>>
}

export function ModuleTable(props: Props) {
    console.log(props.module.name + " -> " + props.shift)

    const onClick = (x: number, y: number) => {
        console.log(x + " -> " + y)
    }

    const renderValue = (x: number, y: number) => {
        const value = props.matrix[y][x].isSelected ? "T" : "F"
        console.log("Render: " + y + " -> " + x + " = " + value)
        return props.matrix[y][x].isSelected ? "T" : "F"
    }

    const renderModule = () => {
        const module = props.module;

        const rows = []
        for (let i = 0; i < module.courses.length; i++) {
            const element = (
                <tr className={styles.tr}>
                    {i == 0 ? <td className={styles.td} rowSpan={module.courses.length}>{module.name}</td> : <></>}
                    <td className={styles.td}>{module.courses[i].name}</td>
                    <td className={styles.td} onClick={(e) => onClick(0, props.shift + i)}>{renderValue(0, props.shift + i)}</td>
                    <td className={styles.td} onClick={(e) => onClick(1, props.shift + i)}>{renderValue(1, props.shift + i)}</td>
                    <td className={styles.td} onClick={(e) => onClick(2, props.shift + i)}>{renderValue(2, props.shift + i)}</td>
                    <td className={styles.td} onClick={(e) => onClick(3, props.shift + i)}>{renderValue(3, props.shift + i)}</td>
                </tr>
            )
            rows.push(element)
        }
        return rows;
    }

    return (
        <>
            {renderModule()}
        </>
    )
}