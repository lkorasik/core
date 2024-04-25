import { useState } from "react";
import { Container } from "../Container/Container";
import { PrimarySeparator } from "../Separator/PrimarySeparator/PrimarySeparator";
import { SecondarySeparator } from "../Separator/SecondarySeparator/SecondarySeparator";
import styles from "./SemesterPlan.module.css";
import { DialogModal } from "../../domain/DialogModal/DialogModal";
import { ModuleDto } from "../../apis/api/modules/ModuleDto";
import { Flex } from "../Flex/Flex";
import { Input } from "../Input/Input";

export interface Props {
    semesterNumber: number
    modules: ModuleDto[]
}

export function SemesterPlan(props: Props) {
    const [showDialog, setShowDialog] = useState<boolean>(false);

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
                <div className={styles.add_button} onClick={() => setShowDialog(true)}>
                    + Добавить
                </div>
            </div>
        )
    }

    const renderDialogItem = (moduleName: string) => {
        return (
            <div className={styles.dialog_item}>
                <Input type="checkbox"/>
                {moduleName}
            </div>
        )
    }

    const renderDialog = () => {
        console.log(showDialog)
        if (showDialog) {
            return (
                <DialogModal close={() => setShowDialog(false)} title="Title">
                    <Flex direction="column">
                        {props.modules.map(x => renderDialogItem(x.name))}
                    </Flex>
                </DialogModal>
            )
        }
    }

    return (
        <Container>
            {renderDialog()}
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