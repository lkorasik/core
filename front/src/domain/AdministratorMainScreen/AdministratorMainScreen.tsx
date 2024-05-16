import styles from "./AdministratorMainScreen.module.css";
import {ActionBar} from "../../base_components/ActionBar/ActionBar";
import { ADMINISTRATOR, COURESE_AND_MODULES, EDUCATIONAL_PROGRAM, STATISTICS } from "../App/App";

export function AdministratorMainScreen() {
    return (
        <div className={styles.mainPageContainer}>
            <ActionBar item={
                [ 
                    {
                        label: "Образовательные программы",    
                        link: ADMINISTRATOR + EDUCATIONAL_PROGRAM
                    },
                    {
                        label: "Курсы и модули",    
                        link: ADMINISTRATOR + COURESE_AND_MODULES
                    },
                    {
                        label: "Статистика",    
                        link: ADMINISTRATOR + STATISTICS
                    }
                ]
            } />
        </div>
    )
}
