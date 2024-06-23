import {CourseForEducationalProgram} from "../../apis/api/course/CourseForEducationalProgram";
import {CoursesBySemesterDto} from "../../apis/api/course/CoursesBySemesterDto";


export enum CoursesStoreActionType {
    AddCourse = "AddCourse",
    AddModuleCourses = "AddModuleCourses",
    RemoveCourse = "RemoveCourse",
    ClearAllCourses = "ClearAllCourses",
    InitSelectedCourses = "InitSelectedCourses",
}

export type CoursesStoreAction =
    { type: CoursesStoreActionType.AddCourse, semesterId: string, course: CourseForEducationalProgram } |
    { type: CoursesStoreActionType.RemoveCourse, courseId: string } |
    { type: CoursesStoreActionType.ClearAllCourses } |
    {
        type: CoursesStoreActionType.InitSelectedCourses,
        allCourses: CourseForEducationalProgram[],
        selectedCourses: CoursesBySemesterDto[],
    } |
    { type: CoursesStoreActionType.AddModuleCourses, courses: CourseForEducationalProgram[] };


interface CoursesStoreState {
    chosenCourses: CourseForEducationalProgram[];
    chosenCoursesBySemesters: CoursesBySemesterDto[];
}

export const coursesStoreReducer = (state: CoursesStoreState | undefined, action: CoursesStoreAction): CoursesStoreState => {
    if (!state) {
        return { chosenCourses: [], chosenCoursesBySemesters: [] };
    }

    const chosenCoursesIds = state.chosenCourses.map(x => x.id);

    switch (action.type) {
        case CoursesStoreActionType.AddCourse:
            if (chosenCoursesIds.includes(action.course.id)) {
                throw new Error(`Course ${action.course.id} is already chosen`);
            }

            const newCoursesBySemesters: CoursesBySemesterDto[] = state.chosenCoursesBySemesters
                .map(x => ({
                    semesterId: x.semesterId,
                    coursesIds: [...x.coursesIds]
                }));
            const coursesBySemester = newCoursesBySemesters.find(x => x.semesterId === action.semesterId);
            if (coursesBySemester) {
                coursesBySemester.coursesIds.push(action.course.id)
            } else {
                newCoursesBySemesters.push({semesterId: action.semesterId, coursesIds: [action.course.id]});
            }

            return {
                chosenCourses: [...state.chosenCourses, action.course],
                chosenCoursesBySemesters: newCoursesBySemesters,
            };
        case CoursesStoreActionType.AddModuleCourses:
            for (const course of action.courses) {
                if (
                    course.moduleId == null
                    || course.moduleId !== action.courses[0].moduleId
                ) {
                    throw new Error("Some of the courses have different educationalModuleId or does not have it at all");
                }
            }
            const requiredCourse = action.courses.find(x => x.requiredSemesterId);
            if (requiredCourse) {
                throw new Error("Should not add required courses to the coursesStore")
            }

            const newChosenCourses = state.chosenCourses.concat(action.courses);
            const chosenCoursesBySemestersCopy = state.chosenCoursesBySemesters
                .map(x => ({
                    semesterId: x.semesterId,
                    coursesIds: [...x.coursesIds]
                }));
            action.courses.forEach(course => {
                const semesterWithCourses = chosenCoursesBySemestersCopy.find(x => x.semesterId === course.semesterEntities[0].id);
                if (semesterWithCourses) {
                    semesterWithCourses.coursesIds.push(course.id);
                } else {
                    chosenCoursesBySemestersCopy.push({
                        semesterId: course.semesterEntities[0].id,
                        coursesIds: [course.id]
                    });
                }
            })
            return {
               chosenCourses: newChosenCourses,
               chosenCoursesBySemesters: chosenCoursesBySemestersCopy,
            };
        case CoursesStoreActionType.RemoveCourse:
            let newState = {...state};
            let chosenCoursesIdsCopy = [...chosenCoursesIds];

            const course = state.chosenCourses.find(x => x.id === action.courseId);
            if (course && course.moduleId) {
                const allCoursesFromModule = state.chosenCourses
                    .filter(x => x.moduleId === course.moduleId);
                for (const moduleCourse of allCoursesFromModule) {
                    newState = removeCourse(newState, moduleCourse.id, chosenCoursesIdsCopy)
                    chosenCoursesIdsCopy = newState.chosenCourses.map(x => x.id);
                }
            } else {
                newState = removeCourse(state, action.courseId, chosenCoursesIds);
            }
            return newState;
        case CoursesStoreActionType.ClearAllCourses:
            return {
                chosenCourses: [],
                chosenCoursesBySemesters: [],
            };
        case CoursesStoreActionType.InitSelectedCourses:
            const selectedCoursesIds = new Set(
                action.selectedCourses
                    .map(x => x.coursesIds)
                    .reduce((previousValue, currentValue) => previousValue.concat(currentValue))
            );
            const chosenCourses: CourseForEducationalProgram[] = [];
            action.allCourses.forEach(x => {
               if (selectedCoursesIds.has(x.id)) {
                   chosenCourses.push(x);
               }
            });
            return {
                chosenCourses: chosenCourses,
                chosenCoursesBySemesters: action.selectedCourses,
            };
        default:
            return state;
    }
}

const removeCourse = (
    state: CoursesStoreState,
    courseId: string,
    chosenCoursesIds: string[]
) => {
    const removeIndex = chosenCoursesIds.indexOf(courseId);
    if (removeIndex === -1) {
        throw new Error(`Course ${courseId} was not found in chosen courses`);
    }
    const coursesWithRemoved = [...state.chosenCourses];
    coursesWithRemoved.splice(removeIndex, 1);

    const coursesBySemestersCopy: CoursesBySemesterDto[] = state.chosenCoursesBySemesters
        .map(x => ({
            semesterId: x.semesterId,
            coursesIds: [...x.coursesIds]
        }));
    const semesterWithCourses = coursesBySemestersCopy.find(x => x.coursesIds.includes(courseId))!;
    const secondRemoveIndex = semesterWithCourses.coursesIds.indexOf(courseId);
    semesterWithCourses.coursesIds.splice(secondRemoveIndex, 1);
    if (semesterWithCourses.coursesIds.length === 0) {
        const semesterIndex = coursesBySemestersCopy.map(x => x.semesterId).indexOf(semesterWithCourses.semesterId)
        coursesBySemestersCopy.splice(semesterIndex, 1);
    }
    return {
        chosenCourses: coursesWithRemoved,
        chosenCoursesBySemesters: coursesBySemestersCopy,
    };
}
