import { useEffect, useState } from "react";
import styles from "./NewStudyPlan.module.css";
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { ModuleDto } from "../../apis/api/modules/ModuleDto";
import { ModuleTable } from "./ModuleTable/ModuleTable";
import { FullModuleDto } from "../../apis/api/modules/FullModuleDto";

export interface CheckBox {
    isSelected: boolean,
    isChangeable: number
}

export function NewStudyPlan() {
    const [matrix, setMatrix] = useState<CheckBox[][]>([])
    const [modules, setModules] = useState<FullModuleDto[]>([])

    const api = useApis()

    useEffect(() => {
        const loadModules = async () => {
            const modules = await api.educationalModulesApi.getAllModules2()

            const newMatrix = []
            for (let i = 0; i < modules.length; i++) {
                for (let j = 0; j < modules[i].courses.length; j++) {
                    const checkBox0: CheckBox = { isSelected: false, isChangeable: 0 }
                    const checkBox1: CheckBox = { isSelected: false, isChangeable: 0 }
                    const checkBox2: CheckBox = { isSelected: false, isChangeable: 0 }
                    const checkBox3: CheckBox = { isSelected: false, isChangeable: 0 }
                    newMatrix.push([checkBox0, checkBox1, checkBox2, checkBox3])
                }
            }
            setMatrix(newMatrix)
            setModules(modules)

            console.log("State matrix: " + newMatrix)
        }
        loadModules().catch(console.error)
    }, [])

    const renderModules = () => {
        let x = 0
        return modules.map(module => {
            let copy = x;
            x = x + module.courses.length;
            return <ModuleTable module={module} shift={copy} matrix={matrix} setMatrix={setMatrix} />
        })
    }

    return (
        <table className={styles.table}>
            <tr className={styles.tr}>
                <td className={styles.td} rowSpan={2} colSpan={2}></td>
                <td className={styles.td} colSpan={2}>1 курс</td>
                <td className={styles.td} colSpan={2}>2 курс</td>
            </tr>
            <tr className={styles.tr}>
                <td className={styles.td}>1 семестр</td>
                <td className={styles.td}>2 семестр</td>
                <td className={styles.td}>3 семестр</td>
                <td className={styles.td}>4 семестр</td>
            </tr>
            {renderModules()}
        </table>
    )
}