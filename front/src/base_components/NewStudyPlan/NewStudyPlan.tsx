import { useEffect, useState } from "react";
import styles from "./NewStudyPlan.module.css";
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { ModuleDto } from "../../apis/api/modules/ModuleDto";

interface CheckBox {
    isSelected: boolean,
    isChangeable: boolean
}

export function NewStudyPlan() {
    const [matrix, setMatrix] = useState<CheckBox[][]>([])
    const [modules, setModules] = useState<ModuleDto[]>([])

    const api = useApis()

    useEffect(() => {
        const loadModules = async () => {
            const modules = await api.educationalModulesApi.getAllModules()

            const newMatrix = []
            for (let i = 0; i < modules.length; i++) {
                const checkBox0: CheckBox = { isSelected: false, isChangeable: false }
                const checkBox1: CheckBox = { isSelected: false, isChangeable: false }
                const checkBox2: CheckBox = { isSelected: false, isChangeable: false }
                const checkBox3: CheckBox = { isSelected: false, isChangeable: false }
                newMatrix.push([checkBox0, checkBox1, checkBox2, checkBox3])
            }
            setMatrix(newMatrix)
            setModules(modules)

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
                    const checkbox: CheckBox = { isSelected: !matrix[i][j].isSelected, isChangeable: matrix[i][j].isChangeable }
                    newRow.push(checkbox)
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
        return matrix[y][x].isSelected ? "T" : "F"
    }

    const renderRows = () => {
        return matrix.map((row, index) => 
            <tr className={styles.tr}>
                <td className={styles.td}>{modules[index].name}</td>
                <td className={styles.td} onClick={(e) => onClick(0, index)}>{renderText(0, index)}</td>
                <td className={styles.td} onClick={(e) => onClick(1, index)}>{renderText(1, index)}</td>
                <td className={styles.td} onClick={(e) => onClick(2, index)}>{renderText(2, index)}</td>
                <td className={styles.td} onClick={(e) => onClick(3, index)}>{renderText(3, index)}</td>
            </tr>
        )
    }

    const renderHeader = () => {
        return (
            <>
                <tr className={styles.tr}>
                    <td className={styles.td} rowSpan={2}></td>
                    <td className={styles.td} colSpan={2}>1 курс</td>
                    <td className={styles.td} colSpan={2}>2 курс</td>
                </tr>
                <tr className={styles.tr}>
                    <td className={styles.td}>1 семестр</td>
                    <td className={styles.td}>2 семестр</td>
                    <td className={styles.td}>3 семестр</td>
                    <td className={styles.td}>4 семестр</td>
                </tr>
            </>
        )
    }

    return (
        <table className={styles.table}>
            {renderHeader()}
            {renderRows()}
        </table>
    )
}