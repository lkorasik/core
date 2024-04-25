import { useEffect, useState } from "react";
import { CredtisBar } from "../CredtisBar/CredtisBar";
import { SemesterPlan } from "../SemesterPlan/SemesterPlan";
import styles from "./StudyPlan.module.css"
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { ModuleDto } from "../../apis/api/modules/ModuleDto";
import { DialogModal } from "../../domain/DialogModal/DialogModal";

export interface Props {
    programId: string
}

export function StudyPlan(props: Props) {
    const [modules, setModules] = useState<ModuleDto[]>([])

    const api = useApis();

    useEffect(() => {
        const loadModule = async () => { 
            const response = await api.educationalModulesApi.getAllModules();

            setModules(response);
        };
        loadModule().catch(console.error);
    }, [])

    return (
        <div>
            <CredtisBar firstSemester={1} secondSemester={2} thirdSemester={3} fourthSemester={4}/>
            <div className={styles.container}>
                <SemesterPlan semesterNumber={1} modules={modules}/>
                <SemesterPlan semesterNumber={2} modules={modules}/>
                <SemesterPlan semesterNumber={3} modules={modules}/>
                <SemesterPlan semesterNumber={4} modules={modules}/>
            </div>
        </div>
    )
}