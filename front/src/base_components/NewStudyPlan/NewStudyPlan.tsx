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

interface SaveObject {
    moduleId: string,
    startYear: number,
    coursesSemester: number[]
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
        }
        loadModules().catch(console.error)
    }, [])

    const save = () => {
        console.log("Save")

        let index = 0
        const result = []
        for (let i = 0; i < modules.length; i++) {
            const moduleInfo: SaveObject = { moduleId: modules[i].id, startYear: 0, coursesSemester: [] };
            for (let j = 0; j < modules[i].courses.length; j++) {
                const checkBoxes = matrix[index].map(x => x.isSelected)
                const position = checkBoxes.findIndex(x => x)
                moduleInfo.coursesSemester.push(position + 1)
                index++;
            }
            result.push(moduleInfo)
        }

        console.log(result)
    }

    const renderModules = () => {
        let x = 0
        return modules.map(module => {
            let copy = x;
            x = x + module.courses.length;
            return <ModuleTable module={module} shift={copy} matrix={matrix} setMatrix={setMatrix} />
        })
    }

    return (
        <>
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
            <button onClick={(e) => save()} >Save</button>
        </>
    )
}