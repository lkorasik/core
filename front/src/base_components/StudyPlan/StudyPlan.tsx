import { useEffect, useState } from "react";
import { CredtisBar } from "../CredtisBar/CredtisBar";
import { SemesterPlan } from "../SemesterPlan/SemesterPlan";
import styles from "./StudyPlan.module.css"
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { ModuleDto } from "../../apis/api/modules/ModuleDto";

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
                <SemesterPlan semesterNumber={1}/>
                <SemesterPlan semesterNumber={2}/>
                <SemesterPlan semesterNumber={3}/>
                <SemesterPlan semesterNumber={4}/>
            </div>
        </div>
    )
}