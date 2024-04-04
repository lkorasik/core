import {FC, useEffect, useMemo, useState} from "react";
import {useApis} from "../../apis/ApiBase/ApiProvider";
import {RecommendationResultDto} from "../../apis/api/recommendation/RecommendationResultDto";
import styles from "./recommendationsContent.module.css";
import {Text} from "../../base_components/Text/Text";
import {RecommendedCourseDto} from "../../apis/api/recommendation/RecommendedCourseDto";
import {ModuleCoursesDto} from "../../apis/api/recommendation/ModuleCoursesDto";
import {Loader} from "../../base_components/Loader/Loader";
import { SemesterDto } from "../../apis/api/recommendation/SemesterDto";


export const RecommendationsContent: FC = () => {
    const apis = useApis();
    const [recommendations, setRecommendations] = useState<RecommendationResultDto | null>(null);
    const [actualSemesters, setActualSemesters] = useState<SemesterDto[]>([]);
    const orderedSemesters = useMemo(
        () => [...actualSemesters].sort((lhs, rhs) => {
            if (lhs.year === rhs.year) {
                return lhs.ordinalNumber - rhs.ordinalNumber;
            }
            return lhs.year - rhs.year;
        }),
        [actualSemesters]);
    const perfectCoursesBySemesterIds = useMemo<Map<string, RecommendedCourseDto[]> | null>(
        () => getCoursesBySemesters(recommendations?.perfectCourses, recommendations?.moduleCourses),
        [recommendations]
    );
    const partiallySuitableCoursesBySemesterIds = useMemo<Map<string, RecommendedCourseDto[]> | null>(
        () => getCoursesBySemesters(recommendations?.partiallySuitableCourses, recommendations?.moduleCourses),
        [recommendations]
    );
    const complementaryCoursesBySemesterIds = useMemo<Map<string, RecommendedCourseDto[]> | null>(
        () => getCoursesBySemesters(recommendations?.complementaryCourses, recommendations?.moduleCourses),
        [recommendations]
    );

    useEffect(() => {
        const loadRecommendations = async () => {
            const recommendationResult = await apis.recommendationsApi.calculateRecommendations();
            const semesters = await apis.semestersApi.getActualSemesters();
            setRecommendations(recommendationResult);
            setActualSemesters(semesters);
        };
        loadRecommendations();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])

    return (
        <div className={styles.container}>
            {(() => {
                if (!recommendations) {
                    return <div className={styles.loader}><Loader /></div>
                }
                return (
                  <>
                      <div className={styles.semesters}>
                          {[1, 2, 3, 4].map(renderSemester)}
                      </div>
                      <Text className={styles.headerText} size={2}>Соответствует вашим интересам и навыкам:</Text>
                      {perfectCoursesBySemesterIds!.size ? (
                          <div className={styles.coursesContainer}>
                              {orderedSemesters.map(x => renderCoursesForSemester(perfectCoursesBySemesterIds!.get(x.id)))}
                          </div>
                      ) : renderNotFound()}

                      <Text className={styles.headerText} size={2}>Соответствует вашим интересам, но не хватает навыков:</Text>
                      {partiallySuitableCoursesBySemesterIds!.size ? (
                          <div className={styles.coursesContainer}>
                              {orderedSemesters.map(x => renderCoursesForSemester(partiallySuitableCoursesBySemesterIds!.get(x.id)))}
                          </div>
                      ) : renderNotFound()}
                      <Text className={styles.headerText} size={2}>Поможет получить недостающие навыки:</Text>
                      {complementaryCoursesBySemesterIds!.size ? (
                          <div className={styles.coursesContainer}>
                              {orderedSemesters.map(x => renderCoursesForSemester(complementaryCoursesBySemesterIds!.get(x.id)))}
                          </div>
                      ) : renderNotFound()}
                  </>
                );
            })()}
        </div>
    );
}

const renderSemester = (semesterNumber: number) => {
    return (
        <div key={semesterNumber} className={styles.semesterItem}>
            <Text className={styles.semesterText} size={2} align={"center"}>{semesterNumber} семестр</Text>
        </div>
    );
}

const renderCoursesForSemester = (courses: RecommendedCourseDto[] | undefined) => {
    if (!courses) {
        return;
    }
    return (
        <div key={courses[0].semesters[0].id} className={styles.semesterBlock}>
            {courses.map(renderCourseCard)}
        </div>
    );
}

const renderCourseCard = (course: RecommendedCourseDto) => {
    return (
      <div key={course.id} className={styles.courseCard}>
          <Text size={2} align={"center"}>{course.name}</Text>
      </div>
    );
}

const renderNotFound = () => {
    return <Text className={styles.notFoundText} size={1.8} align={"center"}>Упс, кажется, мы ничего не нашли</Text>;
}

const getCoursesBySemesters = (
    courses: RecommendedCourseDto[] | undefined,
    coursesByModules: ModuleCoursesDto[] | undefined
) => {
    if (!courses || !coursesByModules) {
        return null;
    }

    const coursesIds = new Set(courses.map(x => x.id));
    const additionalCourses: RecommendedCourseDto[] = [];
    for (const course of courses) {
        if (!course.moduleId) {
            continue;
        }
        const moduleCourses = coursesByModules.find(x => x.moduleId === course.moduleId);
        if (!moduleCourses) {
            throw new Error("No module found for moduleId: " + course.moduleId);
        }
        moduleCourses.courses.forEach(x => {
            if (!coursesIds.has(x.id)) {
                additionalCourses.push(x);
                coursesIds.add(x.id);
            }
        });
    }
    const semesterIdToCourses = new Map<string, RecommendedCourseDto[]>();
    const allCourses = [...courses, ...additionalCourses]
    allCourses.forEach(course => {
        const semesterIds = course.semesters.map(y => y.id);
        for (const semesterId of semesterIds) {
            const semesterCourses = semesterIdToCourses.get(semesterId);
            if (semesterCourses) {
                semesterCourses.push(course)
            } else {
                semesterIdToCourses.set(semesterId, [course]);
            }
        }
    })
    return semesterIdToCourses;
}
