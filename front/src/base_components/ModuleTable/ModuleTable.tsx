import { FullModuleDto } from "../../apis/api/modules/FullModuleDto"
import styles from "./ModuleTable.module.css";

export interface Props {
    module: FullModuleDto
}

export function ModuleTable(props: Props) {
    const renderModule = () => {
        const module = props.module;

        const rows = []
        for (let i = 0; i < module.courses.length; i++) {
            const element = (
                <tr className={styles.tr}>
                    {i == 0 ? <td className={styles.td} rowSpan={module.courses.length}>{module.name}</td> : <></>}
                    <td className={styles.td}>{module.courses[i].name}</td>
                    <td className={styles.td}>0</td>
                    <td className={styles.td}>0</td>
                    <td className={styles.td}>0</td>
                    <td className={styles.td}>0</td>
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