import { useEffect, useState } from "react";
import styles from "./NewStudyPlan.module.css";
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { ModuleDto } from "../../apis/api/modules/ModuleDto";

export function NewStudyPlan() {
    const [matrix, setMatrix] = useState<boolean[][]>([])

    const api = useApis()

    useEffect(() => {
        const loadModules = async () => {
            const modules = await api.educationalModulesApi.getAllModules()

            const newMatrix = []
            for (let i = 0; i < modules.length; i++) {
                newMatrix.push([false, false, false, false])
            }
            setMatrix(newMatrix)

            console.log(newMatrix)
        }
        loadModules().catch(console.error)
    }, [])

    const onClick = (x: number, y: number) => {
        const newMatrix = []
        for (let i = 0; i < matrix.length; i++) {
            const newRow = []
            for (let j = 0; j < matrix[i].length; j++) {
                if ((i == y) && (j == x)) {
                    newRow.push(!matrix[i][j])
                } else {
                    newRow.push(matrix[i][j])
                }
            }
            newMatrix.push(newRow);
        }
        setMatrix(newMatrix)
        console.log(newMatrix)
    }

    const renderText = (x: number, y: number) => {
        return matrix[y][x] ? "T" : "F"
    }

    const renderRows = () => {
        return matrix.map((row, index) => 
            <tr className={styles.tr}>
                <td className={styles.td} onClick={(e) => onClick(0, index)}>{renderText(0, index)}</td>
                <td className={styles.td} onClick={(e) => onClick(1, index)}>{renderText(1, index)}</td>
                <td className={styles.td} onClick={(e) => onClick(2, index)}>{renderText(2, index)}</td>
                <td className={styles.td} onClick={(e) => onClick(3, index)}>{renderText(3, index)}</td>
            </tr>
        )
    }

    return (
        <table className={styles.table}>
            {renderRows()}
        </table>
    )
}