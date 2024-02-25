import React from "react";
import {Text} from "../../base_components/Text/Text";
import styles from "./ChosenCoursesForm.module.css";
import {Flex} from "../../base_components/Flex/Flex";
import {CourseForEducationalProgram} from "../../apis/dto/CourseForEducationalProgram";
import {CardButtonAction, CardType, CourseCard} from "../СourseCard/CourseCard";
import {ScrollContainer} from "../../base_components/ScrollContainer/ScrollContainer";
import {connect, ConnectedProps} from "react-redux";
import {RootState} from "../../index";
import {IAllApisProp, withApis} from "../../apis/ApiBase/ApiProvider";
import {SpecialCoursesHelper} from "../../helpers/SpecialCoursesHelper";

interface Props {
    specialCourses: CourseForEducationalProgram[],
    selectedSemesterId: string,
}

type ExtendedProps = Props & ConnectedProps<typeof reduxConnector> & IAllApisProp;

class ChosenCoursesFormClear extends React.Component<ExtendedProps> {
    public render() {
        const requiredCourses = this.props.specialCourses
            .filter(x => x.requiredSemesterId === this.props.selectedSemesterId);

        return (
            <div>
                <Text
                    size={2}
                    fontWeight={"bold"}
                    color={"#5C5C60"}
                    align={"center"}
                >
                    КУРСЫ В ИНДИВИДУАЛЬНОМ УЧЕБНОМ ПЛАНЕ
                </Text>
                <ScrollContainer className={styles.requiredCoursesBox}>
                    <Text
                        size={1.5}
                        fontWeight={"bold"}
                        color={"#5C5C60"}
                        align={"center"}
                    >
                        ОБЯЗАТЕЛЬНЫЕ КУРСЫ:
                    </Text>
                    <Flex className={styles.flexContainer} direction={"column"} gap={2}>
                        {requiredCourses
                            .map(x =>
                                <CourseCard
                                    key={x.id}
                                    cardType={CardType.Course}
                                    specialCourse={x}
                                    semesterId={this.props.selectedSemesterId}
                                    cardColor={x.educationalModuleId ? "indigo" : "lightBlue"}
                                    cardButtonAction={CardButtonAction.Ignore}
                                    disableShowAdditionalInfo
                                />
                            )
                        }
                    </Flex>
                </ScrollContainer>
                <ScrollContainer className={styles.chosenCoursesBox}>
                    <Text
                        size={1.5}
                        fontWeight={"bold"}
                        color={"#5C5C60"}
                        align={"center"}
                    >
                        КУРСЫ ПО ВЫБОРУ:
                    </Text>
                    <Flex className={styles.flexContainer} direction={"column"} gap={2}>
                        {this.props.chosenCourses
                            .filter(x => SpecialCoursesHelper.doesCourseFitSemester(x, this.props.selectedSemesterId))
                            .map(x =>
                                <CourseCard
                                    key={x.id}
                                    cardType={CardType.Course}
                                    specialCourse={x}
                                    semesterId={this.props.selectedSemesterId}
                                    cardColor={x.educationalModuleId ? "indigo" : "lightBlue"}
                                    cardButtonAction={CardButtonAction.RemoveCourse}
                                    disableShowAdditionalInfo
                                />
                            )
                        }
                    </Flex>
                </ScrollContainer>
                <Flex direction={"row"} justifyContent={"center"} gap={1}>
                    <button
                        disabled={!this.props.selectedSemesterId}
                        className={styles.button}
                        onClick={() => this.onSave()}>
                        <Text
                            size={1.25}
                            color={"white"}
                            align={"center"}
                            fontWeight={"bold"}
                        >
                            СОХРАНИТЬ УЧЕБНЫЙ ПЛАН
                        </Text>
                    </button>
                    <button
                        className={styles.button}
                        onClick={() => this.downloadDocument()}
                    >
                        <Text
                            size={1.25}
                            color={"white"}
                            align={"center"}
                            fontWeight={"bold"}
                        >
                            ЗАГРУЗИТЬ WORD-ФАЙЛ С ПЛАНОМ
                        </Text>
                    </button>
                </Flex>
            </div>
        );
    }

    private async onSave() {
        const chosenCoursesBySemesters = [...this.props.chosenCoursesBySemesters];
        const requiredCourses = this.props.specialCourses.filter(x => x.requiredSemesterId);

        for (const requiredCourse of requiredCourses) {
            const semesterWithCourses = chosenCoursesBySemesters.find(x => x.semesterId === requiredCourse.requiredSemesterId!);
            if (semesterWithCourses) {
                semesterWithCourses.coursesIds.push(requiredCourse.id)
            } else {
                chosenCoursesBySemesters.push({
                    semesterId: requiredCourse.requiredSemesterId!, coursesIds: [requiredCourse.id]
                });
            }
        }

        await this.props.apis.specialCoursesApi.selectCourses({
            coursesBySemesters: chosenCoursesBySemesters
        });
    }

    private async downloadDocument() {
        const result = await this.props.apis.documentsApi.generateDocument();
        console.log("Downloaded document");
        console.log(result);
    }
}

const reduxConnector = connect((state: RootState) => ({
    chosenCourses: state.courses.chosenCourses,
    chosenCoursesBySemesters: state.courses.chosenCoursesBySemesters,
}));

export const ChosenCoursesForm = reduxConnector(withApis(ChosenCoursesFormClear));
