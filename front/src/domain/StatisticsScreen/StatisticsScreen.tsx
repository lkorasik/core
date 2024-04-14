import styles from "./StatisticsScreen.module.css";
import {IAllApisProp, withApis} from "../../apis/ApiBase/ApiProvider";
import {connect, ConnectedProps} from "react-redux";
import React from "react";
import {CourseStatistics} from "../../apis/api/course/CourseStatistics";
import {ObjectInList} from "../../base_components/ObjectList/ObjectList";
import {ObjectListGrid} from "../../base_components/ObjectList/ObjectListGrid";
import { Title } from "../../base_components/Title/Title";

interface State {
    coursesStatistics: CourseStatistics[];
}

type Props = IAllApisProp & ConnectedProps<typeof reduxConnector>;

class CoursesStatisticsScreenClear extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props)
        this.state = {
            coursesStatistics: [],
        };
    }

    public async componentDidMount() {
        const actualSemesters = await this.props.apis.semestersApi.getActualSemesters();
        const actualSemestersIds = actualSemesters.map(x => x.id);

        const coursesStatistics = await this.props.apis.specialCoursesApi
            .getActualSpecialCoursesStatistics({
                semestersId: actualSemestersIds,
            });

        this.setState({
            coursesStatistics: coursesStatistics,
        });
    }

    public render() {
        this.componentDidMount()
        const cards = this.state.coursesStatistics
            .sort((a, b) => a.name > b.name ? 1 : -1)
            .map(x => (
            <ObjectInList
                courseName={x.name}
                studentsCount={x.studentsCount}
                link={"/administrator/courses/" + x.id}
                type={"SpecialCourse"}
                paramNames={["Id", "Name"]}
                paramsValues={[x.id, x.name]}/>
        ));
        return (
            <>
                <div className={styles.container}>
                    <Title>Количество студентов на спецкурсах</Title>
                    <div className={styles.spanContainer}>
                        <span className={styles.leftSpan}>Название спецкурса</span>
                        <span className={styles.rightSpan}>Количество студентов</span>
                        <div className={styles.line}>
                        </div>
                    </div>
                    <ObjectListGrid cards={cards}/>
                </div>
            </>
        )
    }
}

const reduxConnector = connect(
    () => ({
    }));

export const StatisticsScreen = reduxConnector(withApis(CoursesStatisticsScreenClear));
