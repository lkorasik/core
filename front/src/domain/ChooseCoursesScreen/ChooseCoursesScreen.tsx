import "./ChooseCoursesScreen.module.css";
import styles from "./ChooseCoursesScreen.module.css";
import {Text} from "../../base_components/Text/Text";
import React, {FC, useEffect, useState} from "react";
import Select from 'react-select'
import {AvailableCoursesList} from "../AvailibleCoursesList/AvailableCoursesList";
import {ChosenCoursesForm} from "../ChosenCoursesForm/ChosenCoursesForm";
import {Flex} from "../../base_components/Flex/Flex";
import {useApis} from "../../apis/ApiBase/ApiProvider";
import {ProgramInfoDto} from "../../apis/api/programs/ProgramInfoDto";
import {SemesterDto} from "../../apis/api/recommendation/SemesterDto";
import {CourseForEducationalProgram} from "../../apis/api/course/CourseForEducationalProgram";
import {useDispatch} from "react-redux";
import {CoursesStoreActionCreator} from "../../storing/coursesStore/coursesStore.actionCreator";
import {ModuleDto} from "../../apis/api/modules/ModuleDto";
import {TextLoadingPlaceholder} from "../../base_components/TextLoadingPlaceholder/TextLoadingPlaceholder";
import {Button} from "../../base_components/Button/Button";
import {useNavigate} from "react-router-dom";


// Old implementation
export const ChooseCoursesScreen: FC = () => {
    const [program, setEducationalProgram] = useState<ProgramInfoDto | undefined>();
    const [actualSemesters, setActualSemesters] = useState<SemesterDto[] | undefined>();
    const [selectedSemesterId, setSelectedSemesterId] = useState<string | undefined>();
    const [educationalModules, setEducationalModules] = useState<ModuleDto[]>([]);
    const [
        specialCoursesForEducationalProgram,
        setSpecialCoursesForEducationalProgram
    ] = useState<CourseForEducationalProgram[]>([]);
    const [isLoadingCourses, setIsLoadingCourses] = useState<boolean>(true);

    const dispatch = useDispatch();
    const apis = useApis();
    const navigate = useNavigate();

    useEffect(() => {
        const componentDidMount = async () => {
            const program = await apis.educationalProgramsApi.getCurrentEducationalProgram();

            const actualSemesters = await apis.semestersApi.getActualSemesters();
            actualSemesters.sort(compareSemesters);
            const actualSemestersIds = actualSemesters.map(x => x.id);

            const specialCourses = await apis.specialCoursesApi.getCoursesByEducationalProgramAndSemesters({
                programId: program.id,
                semestersIds: actualSemestersIds
            });
            const requiredCourses = new Set(
                specialCourses
                    .filter(x => x.requiredSemesterId)
                    .map(x => x.id)
            );

            const modulesIds = [...new Set(
                specialCourses
                    .filter(x => x.moduleId)
                    .map(x => x.moduleId!)
            )]
            const educationalModules = await apis.educationalModulesApi.getModulesByIds({modulesIds: modulesIds});

            let chosenSpecialCoursesIds = await apis.specialCoursesApi.getSelectedCoursesIds({
                semestersIds: actualSemestersIds
            });
            chosenSpecialCoursesIds = chosenSpecialCoursesIds.map(x => ({
                semesterId: x.semesterId,
                coursesIds: x.coursesIds.filter(x => !requiredCourses.has(x))
            }))
            if (chosenSpecialCoursesIds.length !== 0) {
                dispatch(
                    CoursesStoreActionCreator.initSelectedCourses(specialCourses, chosenSpecialCoursesIds)
                );
            }

            setEducationalProgram(program);
            setActualSemesters(actualSemesters);
            setSelectedSemesterId(actualSemesters[0].id);
            setSpecialCoursesForEducationalProgram(specialCourses);
            setEducationalModules(educationalModules);
            setIsLoadingCourses(false);
        };
        componentDidMount();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const render = () => {
        return (
            <div className={styles.mainPageContainer}>
                {renderHeader()}
                <div className={styles.wrapper}>
                    <Flex className={styles.educationalProgramAndSemesterWrapper} direction={"row"}>
                        {program
                            ? (
                                <Text className={styles.educationalProgramText} size={2} fontWeight={"bold"} align={"left"}>
                                    {program.name}
                                </Text>
                            )
                            : <TextLoadingPlaceholder className={styles.educationalProgramText} />}
                        {renderSemestersSelect()}
                    </Flex>
                    <Flex className={styles.flexContainer} direction={"row"} justifyContent={"space-between"} gap={3}>
                        <div className={styles.flexElement}>
                            <AvailableCoursesList
                                isLoadingCourses={isLoadingCourses}
                                specialCourses={specialCoursesForEducationalProgram}
                                chosenSemesterId={selectedSemesterId!}
                                modules={educationalModules}
                            />
                            <Button onClick={() => navigate("/student/recommendationService")}>
                                Перейти в сервис рекомендаций спецкурсов
                            </Button>
                        </div>
                        <div className={styles.flexElement}>
                            <ChosenCoursesForm
                                specialCourses={specialCoursesForEducationalProgram}
                                selectedSemesterId={selectedSemesterId!}
                            />
                        </div>
                    </Flex>
                </div>
            </div>
        );
    }

    const renderHeader = () => {
        return (
            <div className={styles.header}>
                <Text size={3} fontWeight={"bold"} align={"center"}>Индивидуальный учебный план магистранта</Text>
            </div>
        );
    }

    const renderSemestersSelect = () => {
        if (!actualSemesters) {
            return <Select
                className={styles.semesterSelect}
                placeholder={"Загружаем семестры"}
                isDisabled
            />;
        }

        const semestersOptions = actualSemesters.map(x => ({value: x.id, label: `${x.year}/${x.year + 1} год, семестр ${x.ordinalNumber}`}));

        return(
            <Select className={styles.semesterSelect}
                    options={semestersOptions}
                    value={semestersOptions.find(x => x.value === selectedSemesterId)}
                    onChange={newValue => onSemesterChosen(newValue?.value)}
            />
        );
    }

    const onSemesterChosen = (semesterId?: string) => {
        if (!semesterId){
            return;
        }
        setSelectedSemesterId(semesterId);
    }

    const compareSemesters = (lhs: SemesterDto, rhs: SemesterDto) => {
        if (lhs.year > rhs.year) {
            return 1;
        }
        if (lhs.year < rhs.year) {
            return -1;
        }
        return lhs.ordinalNumber - rhs.ordinalNumber;
    }

    return render();
}
