import React from "react";
import {Flex} from "../../base_components/Flex/Flex";
import {Text} from "../../base_components/Text/Text";
import {Icon, IconType} from "../../icons/Icon";
import styles from "./AvailableCoursesList.module.css"
import {ScrollContainer} from "../../base_components/ScrollContainer/ScrollContainer";
import {IAllApisProp, withApis} from "../../apis/ApiBase/ApiProvider";
import {Loader} from "../../base_components/Loader/Loader";
import {CardButtonAction, CardType, CourseCard} from "../СourseCard/CourseCard";
import {CourseForEducationalProgram} from "../../apis/dto/CourseForEducationalProgram";
import {connect, ConnectedProps} from "react-redux";
import {RootState} from "../../index";
import {EducationalModule} from "../../apis/dto/EducationalModule";
import {SpecialCoursesHelper} from "../../helpers/SpecialCoursesHelper";


interface Props {
    isLoadingCourses: boolean;
    chosenSemesterId: string;
    specialCourses: CourseForEducationalProgram[];
    modules: EducationalModule[];
}

type ExtendedProps = Props & IAllApisProp & ConnectedProps<typeof reduxConnector>;

class AvailableCoursesListClear extends React.Component<ExtendedProps> {
    public render() {
        return (
            <div>
                <Flex direction={"row"} justifyContent={"center"} alignItems={"center"}>
                    <Text className={styles.header} size={2} fontWeight={"bold"} color={"#5C5C60"}>ДОСТУПНЫЕ КУРСЫ:</Text>
                    <div className={styles.space}></div>
                    <Icon className={styles.searchIcon} type={IconType.SearchIcon} />
                    <input className={styles.searchInput} onChange={event => this.onSearchLineUpdated(event)}/>
                </Flex>
                {this.renderCourses()}
            </div>
        );
    }

    private renderCourses() {
        if (this.props.isLoadingCourses) {
            return (
                <Flex className={styles.loader} direction={"row"} justifyContent={"center"}>
                    <Loader />
                </Flex>
            );
        }
        if (this.props.specialCourses.length === 0) {
            return;
        }

        let visibleSpecialCourses = this.filterChosenCourses(this.props.specialCourses);
        visibleSpecialCourses = visibleSpecialCourses.filter(x =>
            x.educationalModuleId
            || (SpecialCoursesHelper.doesCourseFitSemester(x, this.props.chosenSemesterId) && !x.requiredSemesterId)
        );
        const moduleIdToCourses = new Map<string | "NoModule", CourseForEducationalProgram[]>();
        visibleSpecialCourses.forEach(x => {
           const courses = moduleIdToCourses.get(x.educationalModuleId ?? "NoModule");
           if (courses) {
               courses.push(x)
           } else {
               moduleIdToCourses.set(x.educationalModuleId ?? "NoModule", [x]);
           }
        });
        const moduleIdToCoursesObj = Object.fromEntries(moduleIdToCourses);

        const cardElements = Object.entries(moduleIdToCoursesObj).map(x => {
           const moduleId = x[0];
           const courses = x[1];
           if (moduleId === "NoModule") {
               return courses.map(course => (
                   <CourseCard
                       key={course.id}
                       cardType={CardType.Course}
                       cardColor={"lightBlue"}
                       cardButtonAction={CardButtonAction.AddCourse}
                       specialCourse={course}
                       semesterId={this.props.chosenSemesterId}
                   />
               ))
           }
            return <CourseCard
                key={moduleId}
                cardType={CardType.Module}
                cardColor={"indigo"}
                cardButtonAction={CardButtonAction.AddCourse}
                educationalModule={{
                    id: moduleId,
                    name: this.props.modules.find(x => x.id === moduleId)!.name,
                }}
                containedCourses={courses}
            />;
        }).flatMap(x => x);

        return (
            <ScrollContainer className={styles.availableCoursesList}>
                <Flex direction={"column"} gap={1}>{cardElements}</Flex>
            </ScrollContainer>
        );
    }

    private filterChosenCourses(courses: CourseForEducationalProgram[]) {
        const chosenCoursesIds = this.props.chosenCourses.map(x => x.id);
        return courses.filter(x => !chosenCoursesIds.includes(x.id));
    }

    private onSearchLineUpdated(event: React.ChangeEvent<HTMLInputElement>) {
       console.log(event.type);
       console.log(event.target.type);
       console.log(event.target.value);
    }
}

const reduxConnector = connect((state: RootState) => ({
    chosenCourses: state.courses.chosenCourses,
}));

export const AvailableCoursesList = reduxConnector(withApis(AvailableCoursesListClear));
