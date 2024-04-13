import styles from "./CourseScreen.module.css";
import {IAllApisProp, withApis} from "../../apis/ApiBase/ApiProvider";
import {connect, ConnectedProps} from "react-redux";
import React from "react";
import {RootState} from "../../index";
import {Link} from "react-router-dom";
import {EDIT_MODULE_COURSES_SCREEN_URL, MODULE_COURSES_SCREEN_URL} from "../App/App";
import {SpecialCourse} from "../../apis/api/course/SpecialCourse";
import {Control} from "../../apis/api/Control";
import { CloseButton } from "../../base_components/CrudButtons/CloseButton/CloseButton";
import { EditButton } from "../../base_components/CrudButtons/EditButton/EditButton";
import { DeleteButton } from "../../base_components/CrudButtons/DeleteButton/DeleteButton";

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
            courseId : courseIdFromStorage ? courseIdFromStorage : ""})
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
                        <EditButton to={EDIT_MODULE_COURSES_SCREEN_URL + this.state.courseId} />
                        <DeleteButton 
                            to={MODULE_COURSES_SCREEN_URL + this.state.courseModel?.moduleId} 
                            onClick={() => this.deleteCourse()}/>
                        <CloseButton to={MODULE_COURSES_SCREEN_URL + this.state.courseModel?.moduleId} />
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
        await this.props.apis.specialCoursesApi.deleteSpecialCourseById({courseId: this.state.courseId});
    }
}

const reduxConnector = connect(
    (state: RootState) => ({}));

export const CourseScreen = reduxConnector(withApis(CourseScreenClear));
