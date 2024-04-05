import { Link } from "react-router-dom";
import styles from "./StudyPlan.module.css";
import { EDUCATIONAL_PROGRAM_SCREEN_URL } from "../App/App";
import { Flex } from "../../base_components/Flex/Flex";
import { DialogModal } from "../DialogModal/DialogModal";
import { useEffect, useState } from "react";
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { SpecialCourse } from "../../apis/api/course/SpecialCourse";
import { ModuleDto } from "../../apis/api/modules/ModuleDto";

interface Props {}

interface Course {
    id: string
    name: string
}

export function StudyPlan(props: Props) {
    const [selectedSemester, setSelectedSemester] = useState<number>();
    const [selectedBlock, setSelectedBlock] = useState<string>();

    const REQUIRED_COURSES = "Обязательные курсы";
    const SPECIAL_COURSES = "Спецкурсы";
    const SCIENCE_WORK = "НИР";

    const [showDialog, setShowDialog] = useState(false);
    const [dialogTitle, setDialogTitle] = useState("");
    const [courses, setCourses] = useState<SpecialCourse[]>();
    const [modules, setModules] = useState<ModuleDto[]>();

    const [educationalProgramName, setEducationalProgramName] = useState("");
    const credits = [useState(0), useState(0), useState(0), useState(0)];

    const [selectedRequiredCourses1, setSelectedRequiredCourses1] = useState<Course[]>([]);
    const [selectedRequiredCourses2, setSelectedRequiredCourses2] = useState<Course[]>([]);
    const [selectedRequiredCourses3, setSelectedRequiredCourses3] = useState<Course[]>([]);
    const [selectedRequiredCourses4, setSelectedRequiredCourses4] = useState<Course[]>([]);

    const [selectedSpecialCourses1, setSelectedSpecialCourses1] = useState<Course[]>([]);
    const [selectedSpecialCourses2, setSelectedSpecialCourses2] = useState<Course[]>([]);
    const [selectedSpecialCourses3, setSelectedSpecialCourses3] = useState<Course[]>([]);
    const [selectedSpecialCourses4, setSelectedSpecialCourses4] = useState<Course[]>([]);

    const [selectedScienceWork1, setSelectedScienceWork1] = useState<Course[]>([]);
    const [selectedScienceWork2, setSelectedScienceWork2] = useState<Course[]>([]);
    const [selectedScienceWork3, setSelectedScienceWork3] = useState<Course[]>([]);
    const [selectedScienceWork4, setSelectedScienceWork4] = useState<Course[]>([]);

    const [educationalProgramId, setEducationalProgramId] = useState<string>("");

    const [disabled, setDisabled] = useState(false);

    const apis = useApis();

    useEffect(() => {
        const loadCourses = async () => {
            const programs = await apis.specialCoursesApi.getAllCourses();
            setCourses(programs);
        }
        loadCourses().catch(console.error);

        const loadModules = async () => {
            const programs = await apis.educationalModulesApi.getAllModules();
            setModules(programs);
        }
        loadModules().catch(console.error);

        const educationalProgramId = localStorage.getItem("EducationalProgramId")
        console.log("localStorage: " + educationalProgramId);
        localStorage.removeItem("EducationalProgramId")

        if (educationalProgramId != null) {
            const loadEducationalProgram = async () => {
                const program = await apis.educationalProgramsApi.getEducationalProgramById({id: educationalProgramId});

                console.log("Program id: " + program.id);
                console.log("Program name: " + program.title);

                setEducationalProgramName(program.title);
                setEducationalProgramId(program.id);

                for(let i = 0; i < program.recommendedCredits.length; i++) {
                    const [state, setState] = credits[i];
                    setState(program.recommendedCredits[i]);
                }

                for(let i = 0; i < program.semesters.length; i++) {
                    const semester = program.semesters[i];
                    // const courses = semester.requiredCourses.map(x => x)
                    const courses: Course[] = [];
                    for(let j = 0; j < semester.requiredCourses.length; j++) {
                        const course: Course = { id: semester.requiredCourses[j].id, name: semester.requiredCourses[j].name } // todo: fix it!
                        courses.push(course);
                    }

                    switch(i) {
                        case 0:
                            setSelectedRequiredCourses1(courses);
                            break;
                        case 1:
                            setSelectedRequiredCourses2(courses);
                            break;
                        case 2:
                            setSelectedRequiredCourses3(courses);
                            break;
                        case 3:
                            setSelectedRequiredCourses4(courses);
                            break;
                    }
                }

                for (let i = 0; i < program.semesters.length; i++) {
                    const semester = program.semesters[i];
                    // const courses = semester.requiredCourses.map(x => x)
                    const courses: Course[] = [];
                    for (let j = 0; j < semester.specialCourses.length; j++) {
                        const course: Course = { id: semester.specialCourses[j].id, name: semester.specialCourses[j].name } // todo: fix it!
                        courses.push(course);
                    }

                    switch (i) {
                        case 0:
                            setSelectedSpecialCourses1(courses);
                            break;
                        case 1:
                            setSelectedSpecialCourses2(courses);
                            break;
                        case 2:
                            setSelectedSpecialCourses3(courses);
                            break;
                        case 3:
                            setSelectedSpecialCourses4(courses);
                            break;
                    }
                }

                console.log(program.semesters)

                setDisabled(true)
                // Отрабатывает, если мы редактируем направление
            }
            loadEducationalProgram().catch(console.error);
        }
    }, [apis.educationalProgramsApi])

    const save = async () => {
        const semester1 = {
            requiredCourses: selectedRequiredCourses1.map(x => x.id),
            specialCourses: selectedSpecialCourses1.map(x => x.id),
            scienceWorks: selectedScienceWork1.map(x => x.id)
        }
        const semester2 = {
            requiredCourses: selectedRequiredCourses2.map(x => x.id),
            specialCourses: selectedSpecialCourses2.map(x => x.id),
            scienceWorks: selectedScienceWork2.map(x => x.id)
        }
        const semester3 = {
            requiredCourses: selectedRequiredCourses3.map(x => x.id),
            specialCourses: selectedSpecialCourses3.map(x => x.id),
            scienceWorks: selectedScienceWork3.map(x => x.id)
        }
        const semester4 = {
            requiredCourses: selectedRequiredCourses4.map(x => x.id),
            specialCourses: selectedSpecialCourses4.map(x => x.id),
            scienceWorks: selectedScienceWork4.map(x => x.id)
        }
        const request = {
            title: educationalProgramName,
            recommendedCredits: credits.map(x => x[0]),
            semesters: [semester1, semester2, semester3, semester4]
        }
        await apis.educationalProgramsApi.createEducationalProgramList(request)
    }

    const check = (id: string, name: string, semesterNumber: number, selected: Course[], setSelected: React.Dispatch<React.SetStateAction<Course[]>>) => {
        console.log("Check: " + name);

        if (selectedBlock == REQUIRED_COURSES) {
            selected.push({ id: id, name: name });
            setSelected(selected);

            console.log("Required. Semester " + semesterNumber + ": [" + selected.map(x => x.id + ":" + x.name) + "]");
        }
        if (selectedBlock == SPECIAL_COURSES) {
            selected.push({ id: id, name: name });
            setSelected(selected);

            console.log("Special. Semester " + semesterNumber + ": [" + selected.map(x => x.id + ":" + x.name) + "]");
        }
        if (selectedBlock == SCIENCE_WORK) {
            selected.push({ id: id, name: name });
            setSelected(selected);

            console.log("Science. Semester " + semesterNumber + ": [" + selected.map(x => x.id + ":" + x.name) + "]");
        }
    }

    const uncheck = (id: string, name: string, semesterNumber: number, selected: Course[], setSelected: React.Dispatch<React.SetStateAction<Course[]>>) => {
        console.log("Uncheck: " + name);

        if (selectedBlock == REQUIRED_COURSES) {
            setSelected(selected.filter(x => x.id != id));

            console.log("Required. Semester " + semesterNumber + ": [" + selected.map(x => x.id + ":" + x.name) + "]");
        }
        if (selectedBlock == SPECIAL_COURSES) {
            setSelected(selected.filter(x => x.id != id));

            console.log("Special. Semester " + semesterNumber + ": [" + selected.map(x => x.id + ":" + x.name) + "]");
        }
        if (selectedBlock == SCIENCE_WORK) {
            setSelected(selected.filter(x => x.id != id));

            console.log("Science. Semester " + semesterNumber + ": [" + selected.map(x => x.id + ":" + x.name) + "]");
        }
    }

    const isChecked = (id: string) => {
        switch(selectedSemester) {
            case 1:
                if (selectedBlock == REQUIRED_COURSES) {
                    return selectedRequiredCourses1.map(x => x.id).indexOf(id) != -1
                }
                if (selectedBlock == SPECIAL_COURSES) {
                    return selectedSpecialCourses1.map(x => x.id).indexOf(id) != -1
                }
                if (selectedBlock == SCIENCE_WORK) {
                    return selectedScienceWork1.map(x => x.id).indexOf(id) != -1
                }
                break;
            case 2:
                if (selectedBlock == REQUIRED_COURSES) {
                    return selectedRequiredCourses2.map(x => x.id).indexOf(id) != -1
                }
                if (selectedBlock == SPECIAL_COURSES) {
                    return selectedSpecialCourses2.map(x => x.id).indexOf(id) != -1
                }
                if (selectedBlock == SCIENCE_WORK) {
                    return selectedScienceWork2.map(x => x.id).indexOf(id) != -1
                }
                break;
            case 3:
                if (selectedBlock == REQUIRED_COURSES) {
                    return selectedRequiredCourses3.map(x => x.id).indexOf(id) != -1
                }
                if (selectedBlock == SPECIAL_COURSES) {
                    return selectedSpecialCourses3.map(x => x.id).indexOf(id) != -1
                }
                if (selectedBlock == SCIENCE_WORK) {
                    return selectedScienceWork3.map(x => x.id).indexOf(id) != -1
                }
                break;
            case 4:
                if (selectedBlock == REQUIRED_COURSES) {
                    return selectedRequiredCourses4.map(x => x.id).indexOf(id) != -1
                }
                if (selectedBlock == SPECIAL_COURSES) {
                    return selectedSpecialCourses4.map(x => x.id).indexOf(id) != -1
                }
                if (selectedBlock == SCIENCE_WORK) {
                    return selectedScienceWork4.map(x => x.id).indexOf(id) != -1
                }
                break;
        }
        return false;
    }

    const renderDialogItem = (name: string, id: string) => {
        return (
            <>
                <div>
                    <input type="checkbox" defaultChecked={isChecked(id)} onChange={(event) => {
                        if (event.target.checked) {
                            switch(selectedSemester) {
                                case 1:
                                    if (selectedBlock == REQUIRED_COURSES) {
                                        check(id, name, 1, selectedRequiredCourses1, setSelectedRequiredCourses1);
                                    }
                                    if (selectedBlock == SPECIAL_COURSES) {
                                        check(id, name, 1, selectedSpecialCourses1, setSelectedSpecialCourses1);
                                    }
                                    if (selectedBlock == SCIENCE_WORK) {
                                        check(id, name, 1, selectedScienceWork1, setSelectedScienceWork1);
                                    }
                                    break;
                                case 2:
                                    if (selectedBlock == REQUIRED_COURSES) {
                                        check(id, name, 2, selectedRequiredCourses2, setSelectedRequiredCourses2);
                                    }
                                    if (selectedBlock == SPECIAL_COURSES) {
                                        check(id, name, 2, selectedSpecialCourses2, setSelectedSpecialCourses2);
                                    }
                                    if (selectedBlock == SCIENCE_WORK) {
                                        check(id, name, 2, selectedScienceWork2, setSelectedScienceWork2);
                                    }
                                    break;
                                case 3:
                                    if (selectedBlock == REQUIRED_COURSES) {
                                        check(id, name, 3, selectedRequiredCourses3, setSelectedRequiredCourses3);
                                    }
                                    if (selectedBlock == SPECIAL_COURSES) {
                                        check(id, name, 3, selectedSpecialCourses3, setSelectedSpecialCourses3);
                                    }
                                    if (selectedBlock == SCIENCE_WORK) {
                                        check(id, name, 3, selectedScienceWork3, setSelectedScienceWork3);
                                    }
                                    break;
                                case 4:
                                    if (selectedBlock == REQUIRED_COURSES) {
                                        check(id, name, 4, selectedRequiredCourses4, setSelectedRequiredCourses4);
                                    }
                                    if (selectedBlock == SPECIAL_COURSES) {
                                        check(id, name, 4, selectedSpecialCourses4, setSelectedSpecialCourses4);
                                    }
                                    if (selectedBlock == SCIENCE_WORK) {
                                        check(id, name, 4, selectedScienceWork4, setSelectedScienceWork4);
                                    }
                                    break;
                            }
                        } else {
                            switch(selectedSemester) {
                                case 1:
                                    if (selectedBlock == REQUIRED_COURSES) {
                                        uncheck(id, name, 1, selectedRequiredCourses1, setSelectedRequiredCourses1);
                                    }
                                    if (selectedBlock == SPECIAL_COURSES) {
                                        uncheck(id, name, 1, selectedSpecialCourses1, setSelectedSpecialCourses1);
                                    }
                                    if (selectedBlock == SCIENCE_WORK) {
                                        uncheck(id, name, 1, selectedScienceWork1, setSelectedScienceWork1);
                                    }
                                    break;
                                case 2:
                                    if (selectedBlock == REQUIRED_COURSES) {
                                        uncheck(id, name, 2, selectedRequiredCourses2, setSelectedRequiredCourses2);
                                    }
                                    if (selectedBlock == SPECIAL_COURSES) {
                                        uncheck(id, name, 2, selectedSpecialCourses2, setSelectedSpecialCourses2);
                                    }
                                    if (selectedBlock == SCIENCE_WORK) {
                                        uncheck(id, name, 2, selectedScienceWork2, setSelectedScienceWork2);
                                    }
                                    break;
                                case 3:
                                    if (selectedBlock == REQUIRED_COURSES) {
                                        uncheck(id, name, 3, selectedRequiredCourses3, setSelectedRequiredCourses3);
                                    }
                                    if (selectedBlock == SPECIAL_COURSES) {
                                        uncheck(id, name, 3, selectedSpecialCourses3, setSelectedSpecialCourses3);
                                    }
                                    if (selectedBlock == SCIENCE_WORK) {
                                        uncheck(id, name, 3, selectedScienceWork3, setSelectedScienceWork3);
                                    }
                                    break;
                                case 4:
                                    if (selectedBlock == REQUIRED_COURSES) {
                                        uncheck(id, name, 4, selectedRequiredCourses4, setSelectedRequiredCourses4);
                                    }
                                    if (selectedBlock == SPECIAL_COURSES) {
                                        uncheck(id, name, 4, selectedSpecialCourses4, setSelectedSpecialCourses4);
                                    }
                                    if (selectedBlock == SCIENCE_WORK) {
                                        uncheck(id, name, 4, selectedScienceWork4, setSelectedScienceWork4);
                                    }
                                    break;
                            }
                        }
                    }}/>
                    {name}
                </div>
            </>
        )
    }

    const renderDialogList = () => {
        return (
            <>
                <div className={styles.select_list}>
                    {courses?.map(x => renderDialogItem(x.name, x.id))}
                </div>
            </>
        )
    }

    const renderCourse = (label: string, semesterNumber: number) => {
        if (label == REQUIRED_COURSES) {
            switch(semesterNumber) {
                case 1:
                    if (selectedRequiredCourses1.length == 0) {
                        return <div>Ничего не выбрано</div>
                    } else {
                        console.log("Draw: [" + selectedRequiredCourses1.map(x => x.id + ":" + x.name + "]"));
                        return selectedRequiredCourses1.map(x => <div>{x.name}</div>)
                    }
                case 2:
                    if (selectedRequiredCourses2.length == 0) {
                        return <div>Ничего не выбрано</div>
                    } else {
                        console.log("Draw: [" + selectedRequiredCourses2.map(x => x.id + ":" + x.name + "]"));
                        return selectedRequiredCourses2.map(x => <div>{x.name}</div>)
                    }
                case 3:
                    if (selectedRequiredCourses3.length == 0) {
                        return <div>Ничего не выбрано</div>
                    } else {
                        console.log("Draw: [" + selectedRequiredCourses3.map(x => x.id + ":" + x.name + "]"));
                        return selectedRequiredCourses3.map(x => <div>{x.name}</div>)
                    }
                case 4:
                    if (selectedRequiredCourses4.length == 0) {
                        return <div>Ничего не выбрано</div>
                    } else {
                        console.log("Draw: [" + selectedRequiredCourses4.map(x => x.id + ":" + x.name + "]"));
                        return selectedRequiredCourses4.map(x => <div>{x.name}</div>)
                    }
            }
        } else if (label == SPECIAL_COURSES) {
            switch (semesterNumber) {
                case 1:
                    if (selectedSpecialCourses1.length == 0) {
                        return <div>Ничего не выбрано</div>
                    } else {
                        console.log("Draw: [" + selectedSpecialCourses1.map(x => x.id + ":" + x.name + "]"));
                        return selectedSpecialCourses1.map(x => <div>{x.name}</div>)
                    }
                case 2:
                    if (selectedSpecialCourses2.length == 0) {
                        return <div>Ничего не выбрано</div>
                    } else {
                        console.log("Draw: [" + selectedSpecialCourses2.map(x => x.id + ":" + x.name + "]"));
                        return selectedSpecialCourses2.map(x => <div>{x.name}</div>)
                    }
                case 3:
                    if (selectedSpecialCourses3.length == 0) {
                        return <div>Ничего не выбрано</div>
                    } else {
                        console.log("Draw: [" + selectedSpecialCourses3.map(x => x.id + ":" + x.name + "]"));
                        return selectedSpecialCourses3.map(x => <div>{x.name}</div>)
                    }
                case 4:
                    if (selectedSpecialCourses4.length == 0) {
                        return <div>Ничего не выбрано</div>
                    } else {
                        console.log("Draw: [" + selectedSpecialCourses4.map(x => x.id + ":" + x.name + "]"));
                        return selectedSpecialCourses4.map(x => <div>{x.name}</div>)
                    }
            }
        } else if (label == SCIENCE_WORK) {
            switch (semesterNumber) {
                case 1:
                    if (selectedScienceWork1.length == 0) {
                        return <div>Ничего не выбрано</div>
                    } else {
                        console.log("Draw: [" + selectedScienceWork1.map(x => x.id + ":" + x.name + "]"));
                        return selectedScienceWork1.map(x => <div>{x.name}</div>)
                    }
                case 2:
                    if (selectedScienceWork2.length == 0) {
                        return <div>Ничего не выбрано</div>
                    } else {
                        console.log("Draw: [" + selectedScienceWork2.map(x => x.id + ":" + x.name + "]"));
                        return selectedScienceWork2.map(x => <div>{x.name}</div>)
                    }
                case 3:
                    if (selectedScienceWork3.length == 0) {
                        return <div>Ничего не выбрано</div>
                    } else {
                        console.log("Draw: [" + selectedScienceWork3.map(x => x.id + ":" + x.name + "]"));
                        return selectedScienceWork3.map(x => <div>{x.name}</div>)
                    }
                case 4:
                    if (selectedScienceWork4.length == 0) {
                        return <div>Ничего не выбрано</div>
                    } else {
                        console.log("Draw: [" + selectedScienceWork4.map(x => x.id + ":" + x.name + "]"));
                        return selectedScienceWork4.map(x => <div>{x.name}</div>)
                    }
            }
        } else {
            return <div>Ничего не выбрано</div>
        }
    }

    const renderSection = (label: string, dialogTitle: string, semesterNumber: number) => {
        return (
            <>
                <label>
                    {label}:
                </label>
                {renderCourse(label, semesterNumber)}
                <div className={styles.button_container}>
                    <button className={styles.add_button} onClick={() => {
                        setDialogTitle(dialogTitle)
                        setSelectedSemester(semesterNumber)
                        setSelectedBlock(label)

                        console.log("Courses:")
                        console.log(courses)
                        console.log("Modules")
                        console.log(modules)

                        setShowDialog(true)
                    }} disabled={disabled}>
                        + Добавить
                    </button>
                </div>
            </>
        )
    }

    const renderColumn = (semesterNumber: number) => {
        return (
            <>
                <Flex direction={"column"} className={styles.column}>
                    <label className={styles.column_header}>
                        {semesterNumber} семестр
                    </label>
                    <hr className={styles.rule} />
                    {renderSection(REQUIRED_COURSES, "обязательных курсов", semesterNumber)}
                    <hr className={styles.secondary_rule} />
                    {renderSection(SPECIAL_COURSES, "спецкурсов", semesterNumber)}
                    <hr className={styles.secondary_rule} />
                    {renderSection(SCIENCE_WORK, "НИР", semesterNumber)}
                </Flex>
            </>
        )
    }

    const renderDialog = () => {
        return (
            <>
                <DialogModal title={"Выбор " + dialogTitle + ": " + selectedSemester + " семестр"} close={() => setShowDialog(false)}>
                    {renderDialogList()}
                </DialogModal>
            </>
        )
    }

    const renderSemestersBar = () => {
        return (
            <>
                <Flex direction={"row"}>
                    <label className={styles.label}>
                        1 семестр:
                    </label>
                    <input className={styles.input_points} type={"text"} required onChange={(e) => {
                        const [creditsState, setCreditsState] = credits[0];
                        setCreditsState(parseInt(e.target.value));
                    }} value={credits[0][0]} />
                </Flex>
                <Flex direction={"row"}>
                    <label className={styles.label}>
                        2 семестр:
                    </label>
                    <input className={styles.input_points} type={"text"} required onChange={(e) => {
                        const [creditsState, setCreditsState] = credits[1];
                        setCreditsState(parseInt(e.target.value));
                    }} value={credits[1][0]}/>
                </Flex>
                <Flex direction={"row"}>
                    <label className={styles.label}>
                        3 семестр:
                    </label>
                    <input className={styles.input_points} type={"text"} required onChange={(e) => {
                        const [creditsState, setCreditsState] = credits[2];
                        setCreditsState(parseInt(e.target.value));
                    }} value={credits[2][0]} />
                </Flex>
                <Flex direction={"row"}>
                    <label className={styles.label}>
                        4 семестр:
                    </label>
                    <input className={styles.input_points} type={"text"} required onChange={(e) => {
                        const [creditsState, setCreditsState] = credits[3];
                        setCreditsState(parseInt(e.target.value));
                    }} value={credits[3][0]}/>
                </Flex>
            </>
        )
    }

    return (
        <>
            {showDialog && renderDialog()}
            <div id={styles.container}>
                <div className={styles.fontHeader1}>
                    Добавить образовательную программу
                </div>
                <div id={styles.helperButtons}>
                    <Link className={styles.linkOverride} to={EDUCATIONAL_PROGRAM_SCREEN_URL}>
                        <button className={styles.saveButton} onClick={(e) => {
                            console.log(educationalProgramName);
                            console.log(credits.map(x => x[0]));

                            save()
                        }} disabled={disabled}>
                            Сохранить
                        </button>
                    </Link>
                    <Link className={styles.linkOverride} to={EDUCATIONAL_PROGRAM_SCREEN_URL}>
                        <button className={styles.cancelButton} />
                    </Link>
                </div>
                <div>
                    <label className={styles.label}>
                        Название образовательной программы <span className={styles.required}>*</span>
                    </label>
                    <br />
                    <input className={styles.input} type={"text"} required onChange={(e) => setEducationalProgramName(e.target.value)} value={educationalProgramName}/>
                </div>
                <div>
                    <label className={styles.label}>
                        Количество з.е. за спецкурсы:
                    </label>
                </div>
                <Flex direction={"row"}>
                    {renderSemestersBar()}
                </Flex>
                <Flex direction={"row"} className={styles.semester_columns}>
                    {renderColumn(1)}
                    {renderColumn(2)}
                    {renderColumn(3)}
                    {renderColumn(4)}
                </Flex>
            </div>
        </>
    )
}