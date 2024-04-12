import { useEffect, useState } from "react";
import styles from "./StudentModuleCourses.module.css";
import { Grid } from "../../base_components/Grid/Grid";
import { Card } from "../../base_components/Card/Card";
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { AvailableCourseDTO } from "../../apis/api/course/AvailableCourseDTO";

interface Props {}

export function StudentModuleCourses(props: Props) {
    const [availableCourses, setAvailableCourses] = useState<AvailableCourseDTO[]>([]);

    const apis = useApis();
    
    useEffect(() => {
        const loadAvailableCourses = async () => {
            console.log("Load courses")
            
            const availableCourses = await apis.specialCoursesApi.loadAvailableCourses();
            setAvailableCourses(availableCourses);            
        }
        loadAvailableCourses().catch(console.error);
    }, [apis.specialCoursesApi])

    const renderCourses = () => {
        return availableCourses.map(x => <Card
            link={"/" + x.id}
            text={x.name}
            paramNames={["Id", "Name"]}
            paramsValues={[x.id, x.name]} />
        )
    }

    const renderModule = () => {
        return (
            <div>
                <div className={styles.fontHeader1}>
                    Добавить образовательную программу
                </div>
                <Grid cards={renderCourses()} />
            </div>
        )
    }

    return (
        <div id={styles.container}>
            {renderModule()}
        </div>
    )
}