import { useNavigate, useParams } from "react-router-dom";
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
import { SaveButton } from "../../../base_components/Buttons/CrudButtons/SaveButton/SaveButton";
import { UpdateEducationalProgramDto } from "../../../apis/api/programs/UpdateEducationalProgramDto";


export function EditEducationalProgramScreen() {
    const [educationalProgramName, setEducationalProgramName] = useState<string>("");
    const [trainingDirection, setTrainingDirection] = useState<string>("");
    const [years, setYears] = useState<number[]>([]);

    const [shouldRenderStudyPlan, setShouldRenderStudyPlan] = useState(false);

    const { educationalProgramId } = useParams();
    const api = useApis();
    const navigate = useNavigate();

    useEffect(() => {
        const loadModule = async () => { 
            const request = { id: educationalProgramId } as ProgramIdDto
            const response = await api.educationalProgramsApi.getEducationalProgramById(request);

            setEducationalProgramName(response.title);
            setTrainingDirection(response.trainingDirection);

            localStorage.setItem("EducationalModuleId", response.id)
        };
        loadModule().catch(console.error);

        const getAvailableYears = async () => { 
            const request = { id: educationalProgramId } as ProgramIdDto
            const response = await api.educationalProgramsApi.getAvailableYears(request);
            
            setYears(response.map(x => x.startYear))
        };
        getAvailableYears().catch(console.error);
    }, [])

    const save = () => {
        const request = { id: educationalProgramId, name: educationalProgramName, trainingDirection: trainingDirection } as UpdateEducationalProgramDto;
        api.educationalProgramsApi.updateEducationalProgram(request);
        navigate(-1);
    }

    const render = () => {
        const options: { value: string, label: string }[] = [];
        years.forEach(year => options.push({ value: year + "", label: year + "" }))
        return options;
    }

    const renderStudyPlan = () => {
        if (shouldRenderStudyPlan) {
            return <StudyPlan />
        }
    }

    return (
        <Container>
            <Toolbar title="Редактирование образовательной программы">
                <SaveButton to={""} onClick={() => save()}/>
                <CloseButton />
            </Toolbar>
            <InputField 
                isRequired
                onChange={(e) => setEducationalProgramName(e.target.value)} 
                value={educationalProgramName}>
                    Название образовательной программы
            </InputField>
            <InputField 
                isRequired 
                onChange={(e) => setTrainingDirection(e.target.value)}
                value={trainingDirection}>
                    Направление подготовки
            </InputField>
            <NText>Год начала обучения:</NText>
            <Select options={render()} onChange={(e) => setShouldRenderStudyPlan(true)}/>
            {renderStudyPlan()}
        </Container>
    )
}