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
        
        if (props.matrix[y][x].isChangeable != 0) {
            return
        }

        const newMatrix = []
        for (let i = 0; i < props.matrix.length; i++) {
            const newRow = []
            for (let j = 0; j < props.matrix[i].length; j++) {
                let checkbox: CheckBox;
                if ((i == y) && (j == x)) {
                    checkbox = { isSelected: !props.matrix[i][j].isSelected, isChangeable: props.matrix[i][j].isChangeable }
                } else {
                    checkbox = props.matrix[i][j]
                }
                newRow.push(checkbox)
            }
            newMatrix.push(newRow);
        }

        for (let i = 0; i < props.matrix.length; i++) {
            if (i != y) {
                if (newMatrix[y][x].isSelected) {
                    newMatrix[i][x].isChangeable++
                } else {
                    newMatrix[i][x].isChangeable--
                }
            }
        }

        for (let i = 0; i < props.matrix[y].length; i++) {
            if (i != x) {
                if (newMatrix[y][x].isSelected) {
                    newMatrix[y][i].isChangeable++
                } else {
                    newMatrix[y][i].isChangeable--
                }
            }
        }

        console.log(newMatrix)
        props.setMatrix(newMatrix)
    }

    const renderValue = (x: number, y: number) => {
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