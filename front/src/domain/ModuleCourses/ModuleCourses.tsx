import { useEffect, useState } from "react"
import styles from "./ModuleCourses.module.css";
import { Title } from "../../base_components/Title/Title";
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { Link, useParams } from "react-router-dom";
import { GetModuleDto } from "../../apis/api/modules/GetModuleDto";
import { EditButton } from "../../base_components/Buttons/CrudButtons/EditButton/EditButton";
import { DeleteButton } from "../../base_components/Buttons/CrudButtons/DeleteButton/DeleteButton";
import { CloseButton } from "../../base_components/Buttons/CrudButtons/CloseButton/CloseButton";
import { MODULES_SCREEN_URL } from "../App/App";
import { CourseDto } from "../../apis/api/modules/CourseDto";
import { Card } from "../../base_components/Card/Card";
import { AddButton } from "../../base_components/Buttons/AddButton/AddButton";
import { Grid } from "../../base_components/Grid/Grid";
import { DeleteModuleDto } from "../../apis/api/modules/DeleteModuleDto";

export function ModuleCourses() {
    const [moduleName, setModuleName] = useState("")
    const [courses, setCourses] = useState<CourseDto[]>([])

    const api = useApis();
    console.log("Render")

    const { moduleId } = useParams();

    useEffect(() => {
        const loadModule = async () => { 
            const request = { id: moduleId } as GetModuleDto 
            const response = await api.educationalModulesApi.getModuleById(request);

            setModuleName(response.name);
            setCourses(response.courses);

            localStorage.setItem("EducationalModuleId", response.id)
        };
        loadModule().catch(console.error);
    }, [])

    const deleteModule = () => {
        const dto = { id: moduleId} as DeleteModuleDto;
        api.educationalModulesApi.deleteModuleById(dto);
    }

    const renderCourses = () => {
        return courses.map(x => 
            <Card
                link={"/administrator/courses/" + x.id}
                text={x.name}
                type={"Course"}
                paramNames={["Id", "Name"]}
                paramsValues={[x.id, x.name]} />);
    }
    
    return (
        <>
            <div className={styles.container}>
                <div className={styles.header}>
                    <Title>{moduleName}</Title>
                    <div className={styles.helperButtons}>
                        <EditButton to={MODULES_SCREEN_URL} />
                        <DeleteButton to={MODULES_SCREEN_URL} onClick={deleteModule}/>
                        <CloseButton />
                    </div>
                </div>
                <Grid cards={renderCourses()} />
                <Link to={"/administrator/courses/add"}>
                    <AddButton/>
                </Link>
            </div>
        </>
    )
}