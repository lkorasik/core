import { Container } from "../Container/Container";
import { PrimarySeparator } from "../Separator/PrimarySeparator/PrimarySeparator";
import { SecondarySeparator } from "../Separator/SecondarySeparator/SecondarySeparator";
import styles from "./SemesterPlan.module.css";

export interface Props {
    semesterNumber: number
}

export function SemesterPlan(props: Props) {
    const renderListItem = () => {
        return (
            <li>
                <div className={styles.list_item}>
                    <div>
                        Text
                    </div>
                    <button>X</button>
                </div>
            </li>
        )
    }

    return (
        <Container>
            <div className={styles.column_header}>
                {props.semesterNumber} семестр
            </div>
            <PrimarySeparator />
            <div className={styles.section_header}>
                Обязательные курсы
                <ul>
                    {renderListItem()}
                </ul>
                <div className={styles.add_button}>
                    + Добавить
                </div>
            </div>
            <SecondarySeparator />
            <div className={styles.section_header}>
                Спецкурсы
                <ul>
                    {renderListItem()}
                </ul>
                <div className={styles.add_button}>
                    + Добавить
                </div>
            </div>
            <SecondarySeparator />
            <div className={styles.section_header}>
                НИР
                <ul>
                    {renderListItem()}
                </ul>
                <div className={styles.add_button}>
                    + Добавить
                </div>
            </div>
        </Container>
    )
}