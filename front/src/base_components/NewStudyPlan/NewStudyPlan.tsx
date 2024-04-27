import { useEffect, useState } from "react";
import styles from "./NewStudyPlan.module.css";
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { ModuleDto } from "../../apis/api/modules/ModuleDto";

export function NewStudyPlan() {
    const [modules, setModules] = useState<ModuleDto[]>([])

    const api = useApis()

    useEffect(() => {
        const loadModules = async () => {
            const modules = await api.educationalModulesApi.getAllModules();
            setModules(modules)
        }
        loadModules().catch(console.error)
    })

    const renderModule = (module: ModuleDto) => {
        return (
            <tr className={styles.tr}>
                <td className={styles.td}>{module.name}</td>
                <td className={styles.td}>O</td>
                <td className={styles.td}>O</td>
                <td className={styles.td}>O</td>
                <td className={styles.td}>O</td>
            </tr>
        )
    }

    const renderModules = () => {
        return modules.map(module => renderModule(module))
    } 

    const renderHeader = () => {
        return (
            <tr className={styles.tr}>
                <td className={styles.td}>Название</td>
                <td className={styles.td}>1</td>
                <td className={styles.td}>2</td>
                <td className={styles.td}>3</td>
                <td className={styles.td}>4</td>
            </tr>
        )
    }

    return (
        <table className={styles.table}>
            {renderHeader()}
            {renderModules()}
        </table>
    )
}