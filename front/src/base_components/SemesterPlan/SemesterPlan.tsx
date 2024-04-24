import { useState } from "react";
import { Container } from "../Container/Container";
import { PrimarySeparator } from "../Separator/PrimarySeparator/PrimarySeparator";
import { SecondarySeparator } from "../Separator/SecondarySeparator/SecondarySeparator";
import styles from "./SemesterPlan.module.css";

export interface Props {
    semesterNumber: number
}

export function SemesterPlan(props: Props) {
    const [requiredCourses, setRequiredCourses] = useState<string>();
    const [specialCourses, setSpecialCourses] = useState<string>();
    const [scienceWork, setScienceWork] = useState<string>();

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

    const renderSection = (sectionHeader: string) => {
        return (
            <div className={styles.section_header}>
                {sectionHeader}
                <ul>
                    {renderListItem()}
                </ul>
                <div className={styles.add_button}>
                    + Добавить
                </div>
            </div>
        )
    }

    return (
        <Container>
            <div className={styles.column_header}>
                {props.semesterNumber} семестр
            </div>
            <PrimarySeparator />
            {renderSection("Обязательные курсы")}
            <SecondarySeparator />
            {renderSection("Спецкурсы")}
            <SecondarySeparator />
            {renderSection("НИР")}
        </Container>
    )
}