import styles from "./NewStudyPlan.module.css";
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { ModuleTable } from "./ModuleTable/ModuleTable";
import { FullModuleDto } from "../../apis/api/modules/FullModuleDto";
import { CheckBox } from "../../domain/EducationalProgramScreen/EditEducationalProgramScreen/EditEducationalProgramScreen";

export interface Props {
    matrix: CheckBox[][],
    setMatrix: React.Dispatch<React.SetStateAction<CheckBox[][]>>
    modules: FullModuleDto[]
}

export function NewStudyPlan(props: Props) {
    const api = useApis()

    const renderModules = () => {
        let x = 0
        return props.modules.map(module => {
            let copy = x;
            x = x + module.courses.length;
            return <ModuleTable module={module} shift={copy} matrix={props.matrix} setMatrix={props.setMatrix} />
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
        </>
    )
}