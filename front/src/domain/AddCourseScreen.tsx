import Select from 'react-select'
import { Container } from "../base_components/Container/Container";
import { InputField } from "../base_components/InputField/InputField";
import { Toolbar } from "../base_components/Toolbar/Toolbar";
import { SaveButton } from '../base_components/Buttons/CrudButtons/SaveButton/SaveButton';
import { CloseButton } from '../base_components/Buttons/CrudButtons/CloseButton/CloseButton';
import { ADMINISTRATOR, COURESE_AND_MODULES } from './App/App';
import { useState } from 'react';
import { CreateCourseDto } from '../apis/api/course/CreateCourseDto';
import { useApis } from '../apis/ApiBase/ApiProvider';

export function AddCourseScreen() {
    const [name, setName] = useState("")
    const [department, setDepartment] = useState("")
    const [teacher, setTeacher] = useState("")
    const [credits, setCredits] = useState(0)
    const [controlType, setControlType] = useState("Test")
    const [description, setDescription] = useState("")

    const api = useApis()

    const createCourse = () => {
        const request: CreateCourseDto = {
            moduleId: localStorage.getItem("EducationalModuleId")!,
            name: name,
            credits: credits,
            controlType: controlType,
            department: department,
            teacher: teacher,
            description: description
        }
        api.specialCoursesApi.createCourse(request);
    }

    const renderOptions = () => {
        return [{ label: "Зачет", value: "Test" }, { label: "Экзамен", value: "Exam" }]
    }

    return (
        <Container>
            <Toolbar title="Добавить курс">
                <SaveButton to={ADMINISTRATOR + COURESE_AND_MODULES} onClick={createCourse}/>
                <CloseButton />
            </Toolbar>
            <InputField isRequired onChange={(e) => setName(e.target.value)}>
                Назавние курса
            </InputField>
            <InputField isRequired onChange={(e) => setDepartment(e.target.value)}>
                Назавние кафедры
            </InputField>
            <InputField isRequired onChange={(e) => setTeacher(e.target.value)}>
                Преподаватель
            </InputField>
            <InputField type={"number"} isRequired onChange={(e) => setCredits(parseInt(e.target.value))}>
                Количество зачетных единиц
            </InputField>
            <Select options={renderOptions()} defaultValue={renderOptions()[0]} onChange={(e) => setControlType(e?.value!)}/>
            <InputField isRequired onChange={(e) => setDescription(e.target.value)}>
                Описание курса
            </InputField>
        </Container>
    )
}