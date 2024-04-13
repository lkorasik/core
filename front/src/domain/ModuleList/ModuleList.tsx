import { useEffect, useState } from "react";
import styles from "./ModuleList.module.css";
import { Grid } from "../../base_components/Grid/Grid";
import { Card } from "../../base_components/Card/Card";
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { AvailableCourseDTO } from "../../apis/api/course/AvailableCourseDTO";
import { AvailableModuleDTO } from "../../apis/api/course/AvailableModuleDTO";
import { addAbortSignal } from "stream";
import { Button } from "../../base_components/Button/Button";

interface Props {}

export function ModuleList(props: Props) {
    const [availableCourses, setAvailableCourses] = useState<AvailableModuleDTO[]>();

    const apis = useApis();
    
    useEffect(() => {
        const loadAvailableCourses = async () => {
            console.log("Load courses")
            
            const availableCourses = await apis.specialCoursesApi.loadAvailableCourses();
            setAvailableCourses(availableCourses);            

            console.log(availableCourses);
        }
        loadAvailableCourses().catch(console.error);
    }, [apis.specialCoursesApi]);

    const renderCourse = (course: AvailableCourseDTO) => {
        return (
            <Card
                link={"/" + course.id}
                text={course.name}
                type={"EducationalProgram"}
                paramNames={["Id", "Name"]}
                paramsValues={[course.id, course.name]} />
        )
    }

    const renderCourses = (module: AvailableModuleDTO) => {
        return module.courses.map(x => renderCourse(x))
    }

    const renderModule = (module: AvailableModuleDTO) => {
        return (
            <>
                <div className={styles.headerContainer}>
                    <div className={styles.fontHeader1}>
                        Модуль: {module.name}
                    </div>
                    <Button className={styles.button}>
                        Выбрать
                    </Button>
                </div>
                <Grid cards={renderCourses(module)}/>
            </>
        )
    }

    const renderModules = () => {
        return (
            <div>
                {availableCourses?.map(x => renderModule(x))}
            </div>
        )
    }

    return (
        <div id={styles.container}>
            {renderModules()}
        </div>
    )
}