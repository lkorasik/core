import { useParams } from "react-router-dom";
import { Toolbar } from "../../../base_components/Toolbar/Toolbar";
import { CloseButton } from "../../../base_components/Buttons/CrudButtons/CloseButton/CloseButton";
import { Container } from "../../../base_components/Container/Container";
import { InputField } from "../../../base_components/InputField/InputField";
import { useEffect, useState } from "react";
import styles from "./EditEducationalProgramScreen.module.css";
import Select from 'react-select'
import { NText } from "../../../base_components/NText/NText";
import { StudyPlan } from "../../../base_components/StudyPlan/StudyPlan";
import { useApis } from "../../../apis/ApiBase/ApiProvider";
import { ProgramIdDto } from "../../../apis/api/programs/ProgramIdDto";


export function EditEducationalProgramScreen() {
    const [educationalProgramName, setEducationalProgramName] = useState<string>("");
    const [trainingDirection, setTrainingDirection] = useState<string>("");

    const { educationalProgramId } = useParams();
    const api = useApis();

    useEffect(() => {
        const loadModule = async () => { 
            const request = { id: educationalProgramId } as ProgramIdDto
            const response = await api.educationalProgramsApi.getEducationalProgramById(request);

            setEducationalProgramName(response.title);

            localStorage.setItem("EducationalModuleId", response.id)
        };
        loadModule().catch(console.error);
    }, [])

    return (
        <Container>
            <Toolbar title="Редактирование образовательной программы">
                <CloseButton />
            </Toolbar>
            <InputField 
                isRequired 
                value={educationalProgramName}>
                    Название образовательной программы
            </InputField>
            <InputField 
                isRequired 
                value={trainingDirection}>
                    Направление подготовки
            </InputField>
            <NText>Год начала обучения:</NText>
            <Select options={[]}/>
            <StudyPlan />
        </Container>
    )
}