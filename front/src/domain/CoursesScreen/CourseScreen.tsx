import styles from "./CourseScreen.module.css";
import {IAllApisProp, withApis} from "../../apis/ApiBase/ApiProvider";
import {connect, ConnectedProps} from "react-redux";
import React from "react";
import {RootState} from "../../index";
import {Link} from "react-router-dom";
import {EDIT_MODULE_COURSES_SCREEN_URL, MODULE_COURSES_SCREEN_URL} from "../App/App";
import {SpecialCourse} from "../../apis/dto/SpecialCourse";
import {Control} from "../../apis/dto/Control";

interface State {
    courseId: string;
    courseModel?: SpecialCourse;
}

type Props = IAllApisProp & ConnectedProps<typeof reduxConnector>;

class CourseScreenClear extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props)
        this.state = {
            courseId: "",
            courseModel: undefined,
        };
    }

    public async componentDidMount() {
        const courseIdFromStorage = localStorage.getItem("SpecialCourseId")
        const courseModel = await this.props.apis.specialCoursesApi.getCourseById({
            specialCourseId : courseIdFromStorage ? courseIdFromStorage : ""})
        this.setState({
            ...this.state,
            courseModel: courseModel,
            courseId: courseIdFromStorage ? courseIdFromStorage : "",
        });
    }

    public render() {
        this.componentDidMount()
        return (
            <>
                <div id={styles.container}>
                    <div className={styles.fontHeader1}>
                        Курс: {this.state.courseModel?.name}
                    </div>
                    <div id={styles.helperButtons}>
                        <Link className={styles.linkOverride}
                              to={EDIT_MODULE_COURSES_SCREEN_URL + this.state.courseId}>
                            <button className={styles.editButton}/>
                        </Link>
                        <Link className={styles.linkOverride}
                              to={MODULE_COURSES_SCREEN_URL + this.state.courseModel?.educationalModuleId}>
                            <button className={styles.deleteButton} onClick={() => {
                                this.deleteCourse()}}/>
                        </Link>
                        <Link className={styles.linkOverride}
                              to={MODULE_COURSES_SCREEN_URL + this.state.courseModel?.educationalModuleId}>
                            <button className={styles.cancelButton}/>
                        </Link>
                    </div>
                    <div>
                        <label className={styles.label}>
                            Название Кафедры
                        </label>
                        <br/>
                        <span className={styles.input}>
                            {this.state.courseModel?.department}
                        </span>
                    </div>
                    <div>
                        <label className={styles.label}>
                        Преподаватель
                        </label>
                        <br/>
                        <span className={styles.input}>
                            {this.state.courseModel?.teacherName}
                        </span>
                    </div>
                    <div>
                        <label className={styles.label}>
                        Количество зачетных единиц
                        </label>
                        <br/>
                        <span className={styles.input}>
                            {this.state.courseModel?.creditsCount}
                        </span>
                    </div>
                    <div>
                        <label className={styles.label}>
                        Тип отчётности по курсу
                        </label>
                        <br/>
                        <span className={styles.input}>
                            {this.state.courseModel?.control == Control.Test ? "Зачет" : "Экзамен"}
                        </span>
                    </div>
                    <div>
                        <label className={styles.label}>
                        Описание курса
                        </label>
                        <br/>
                        <text className={styles.inputLong}>
                            {this.state.courseModel?.description}
                        </text>
                    </div>
                </div>
            </>
        )
    }

    private async deleteCourse() {
        await this.props.apis.specialCoursesApi.deleteSpecialCourseById({specialCourseId: this.state.courseId});
    }
}

const reduxConnector = connect(
    (state: RootState) => ({}));

export const CourseScreen = reduxConnector(withApis(CourseScreenClear));
