import React from "react";
import styles from "./StudentScreen.module.css";
import { ActionBar } from "../../base_components/ActionBar/ActionBar";

interface Props {}

export const StudentScreen: React.FC<Props> = (props: Props) => {
    return (
        <div id="root" className={styles.mainPageContainer}>
            <ActionBar item={
                [ 
                    {
                        label: "Учебный план",    
                        link: "/student/student_plan"
                    },
                    {
                        label: "Курсы и модули",    
                        link: "/student/courses_and_modules"
                    }
                ]
            } />
        </div>
    )
}