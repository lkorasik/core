import { CredtisBar } from "../CredtisBar/CredtisBar";
import { SemesterPlan } from "../SemesterPlan/SemesterPlan";
import styles from "./StudyPlan.module.css"

export function StudyPlan() {
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