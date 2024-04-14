import styles from "./AdministratorMainScreen.module.css";
import {ActionBar} from "../../base_components/ActionBar/ActionBar";

export function AdministratorMainScreen() {
    return (
        <div id="root" className={styles.mainPageContainer}>
            <ActionBar item={
                [ 
                    {
                        label: "Образовательные программы",    
                        link: "/administrator/educational_program"
                    },
                    {
                        label: "Курсы и модули",    
                        link: "/administrator/courses_and_modules"
                    },
                    {
                        label: "Статистика",    
                        link: "/administrator/statistics"
                    }
                ]
            } />
        </div>
    )
}
