import styles from "./AddEducationalProgramScreen.module.css";
import { EDUCATIONAL_PROGRAM_SCREEN_URL } from "../../App/App";
import { DialogModal } from "../../DialogModal/DialogModal";
import { useEffect, useState } from "react";
import { useApis } from "../../../apis/ApiBase/ApiProvider";
import { SpecialCourse } from "../../../apis/api/course/SpecialCourse";
import { ModuleDto } from "../../../apis/api/modules/ModuleDto";
import { CloseButton } from "../../../base_components/CrudButtons/CloseButton/CloseButton";
import { Container } from "../../../base_components/Container/Container";
import { Toolbar } from "../../../base_components/Toolbar/Toolbar";
import { SaveButton } from "../../../base_components/CrudButtons/SaveButton/SaveButton";
import { InputField } from "../../../base_components/InputField/InputField";
import { CreateProgramDto } from "../../../apis/api/programs/CreateProgramDto";

interface Props {}

interface Course {
    id: string
    name: string
}

export function AddEducationalProgramScreen(props: Props) {
    const [name, setName] = useState("");
    const [trainingDirection, setTrainingDirection] = useState("");

    const apis = useApis();

    const save = () => {
        const request = { name: name, trainingDirection: trainingDirection } as CreateProgramDto;
        apis.educationalProgramsApi.createEducationalProgram(request)
    }

    return (
        <>
            <Container>
                <Toolbar title="Добавить образовательную программу">
                    <SaveButton to={EDUCATIONAL_PROGRAM_SCREEN_URL} onClick={() => save()} />
                    <CloseButton />
                </Toolbar>
                <InputField 
                    isRequired 
                    onChange={(e) => setName(e.target.value)} 
                    value={name}>
                        Название образовательной программы
                </InputField>
                <InputField 
                    isRequired 
                    onChange={(e) => setTrainingDirection(e.target.value)} 
                    value={trainingDirection}>
                        Направление подготовки
                </InputField>
            </Container>
        </>
    )
}