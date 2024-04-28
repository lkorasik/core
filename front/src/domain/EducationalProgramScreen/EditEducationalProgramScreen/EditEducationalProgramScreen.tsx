import { useNavigate, useParams } from "react-router-dom";
import { Toolbar } from "../../../base_components/Toolbar/Toolbar";
import { CloseButton } from "../../../base_components/Buttons/CrudButtons/CloseButton/CloseButton";
import { Container } from "../../../base_components/Container/Container";
import { InputField } from "../../../base_components/InputField/InputField";
import { useEffect, useState } from "react";
import styles from "./EditEducationalProgramScreen.module.css";
import Select from 'react-select'
import { NText } from "../../../base_components/NText/NText";
import { useApis } from "../../../apis/ApiBase/ApiProvider";
import { ProgramIdDto } from "../../../apis/api/programs/ProgramIdDto";
import { SaveButton } from "../../../base_components/Buttons/CrudButtons/SaveButton/SaveButton";
import { UpdateEducationalProgramDto } from "../../../apis/api/programs/UpdateEducationalProgramDto";
import { NewStudyPlan } from "../../../base_components/NewStudyPlan/NewStudyPlan";
import { StudyPlanDto } from "../../../apis/api/programs/StudyPlanDto";
import { ModuleSelectionDto } from "../../../apis/api/programs/ModuleSelectionDto";
import { CourseSelectionDto } from "../../../apis/api/programs/CourseSelectionDto";
import { FullModuleDto } from "../../../apis/api/modules/FullModuleDto";
import { GetStudyPlanDto } from "../../../apis/api/programs/GetStudyPlanDto";
import { StudyPlanDto2 } from "../../../apis/api/programs/StudyPlanDto2";

export interface CheckBox {
    isSelected: boolean,
    isChangeable: number
}

export function EditEducationalProgramScreen() {
    const [educationalProgramName, setEducationalProgramName] = useState<string>("");
    const [trainingDirection, setTrainingDirection] = useState<string>("");
    const [years, setYears] = useState<number[]>([]);
    
    const [matrix, setMatrix] = useState<CheckBox[][]>([])
    const [modules, setModules] = useState<FullModuleDto[]>([])

    const [shouldRenderStudyPlan, setShouldRenderStudyPlan] = useState<number>(0);
    const [studyPlan, setStudyPlan] = useState<StudyPlanDto2>()

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

        const loadModules = async () => {
            const modules = await api.educationalModulesApi.getAllModules2()

            const newMatrix = []
            for (let i = 0; i < modules.length; i++) {
                for (let j = 0; j < modules[i].courses.length; j++) {
                    const checkBox0: CheckBox = { isSelected: false, isChangeable: 0 }
                    const checkBox1: CheckBox = { isSelected: false, isChangeable: 0 }
                    const checkBox2: CheckBox = { isSelected: false, isChangeable: 0 }
                    const checkBox3: CheckBox = { isSelected: false, isChangeable: 0 }
                    newMatrix.push([checkBox0, checkBox1, checkBox2, checkBox3])
                }
            }
            setMatrix(newMatrix)
            setModules(modules)
        }
        loadModules().catch(console.error)
    }, [])

    const loadStudyPlan = (year: number) => {
        const loadStudyPlan = async () => {
            const request: GetStudyPlanDto = { programId: educationalProgramId!, startYear: year }
            const response = await api.educationalProgramsApi.getStudyPlan(request)

            const newMatrix = []
            for (let i = 0; i < modules.length; i++) {
                for (let j = 0; j < modules[i].courses.length; j++) {
                    const checkBox0: CheckBox = { isSelected: false, isChangeable: 0 }
                    const checkBox1: CheckBox = { isSelected: false, isChangeable: 0 }
                    const checkBox2: CheckBox = { isSelected: false, isChangeable: 0 }
                    const checkBox3: CheckBox = { isSelected: false, isChangeable: 0 }
                    newMatrix.push([checkBox0, checkBox1, checkBox2, checkBox3])
                }
            }
            setMatrix(newMatrix)
            setStudyPlan(response)

            console.log(response)
        }
        loadStudyPlan().catch(console.error)
    }

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

    const renderNewStudyPlan = () => {
        if (shouldRenderStudyPlan) {
            return <NewStudyPlan matrix={matrix} setMatrix={setMatrix} modules={modules}/>
        }
    }

    const buildStudyPlan = () => {
        let index = 0

        const result: StudyPlanDto = { startYear: years[0], modules: [] }
        for (let i = 0; i < modules.length; i++) {
            const moduleInfo: ModuleSelectionDto = { moduleId: modules[i].id, courses: [] };
            for (let j = 0; j < modules[i].courses.length; j++) {
                const checkBoxes = matrix[index].map(x => x.isSelected)
                const position = checkBoxes.findIndex(x => x)
                if (position == -1) {
                    index++;
                    continue
                }
                const info: CourseSelectionDto = { courseId: modules[i].courses[j].id, semester: position + 1 }
                moduleInfo.courses.push(info)
                index++;
            }
            result.modules.push(moduleInfo)
        }
        
        return result;
    }

    const saveStudyPlan = () => {
        const result = buildStudyPlan()
        api.educationalProgramsApi.saveStudyPlan(result)
    }

    return (
        <Container>
            <Toolbar title="Редактирование образовательной программы">
                <SaveButton to={""} onClick={() => {
                    save()
                    saveStudyPlan()
                }
                }/>
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
            <Select options={render()} onChange={(e) => {
                const year = parseInt(e?.value!)
                loadStudyPlan(year)
                return setShouldRenderStudyPlan(year)
            }}/>
            {renderNewStudyPlan()}
        </Container>
    )
}