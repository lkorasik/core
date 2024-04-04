import styles from "./EditModuleCourseScreen.module.css";
import {IAllApisProp, withApis} from "../../apis/ApiBase/ApiProvider";
import {connect, ConnectedProps} from "react-redux";
import React from "react";
import {RootState} from "../../index";
import {Link} from "react-router-dom";
import {COURSES_SCREEN_URL} from "../App/App";
import {Control} from "../../apis/api/Control";

interface State {
    specialCourseId: string;
    courseName: string;
    department: string;
    teacherName: string;
    creditsCount: number;
    controlType: Control;
    courseDescription: string;
}

type Props = IAllApisProp & ConnectedProps<typeof reduxConnector>;

class EditModuleCourseScreenClear extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props)
        this.state = {
            specialCourseId: "",
            courseName: "",
            department: "",
            teacherName: "",
            creditsCount: 0,
            controlType: Control.Test,
            courseDescription: "",
        };
    }

    public async componentDidMount() {
        const specialCourseId = localStorage.getItem("SpecialCourseId")
        const specialCourse = await this.props.apis.specialCoursesApi
            .getCourseById({courseId: specialCourseId ? specialCourseId : ""});

        this.setState({
            ...this.state,
            specialCourseId: specialCourseId ? specialCourseId : "",
            courseName: specialCourse.name,
            department: specialCourse.department ? specialCourse.department : "",
            teacherName: specialCourse.teacherName ? specialCourse.teacherName : "",
            creditsCount: specialCourse.creditsCount,
            controlType: specialCourse.control,
            courseDescription: specialCourse.description
        });
    }

    public onChangeCourseName(courseName: string) {
        this.setState({
            ...this.state,
            courseName: courseName,
        });
    }

    public onChangeCreditsCount(creditsCount: string) {
        this.setState({
            ...this.state,
            creditsCount: parseInt(creditsCount),
        });
    }

    public onChangeControlType(controlType: string) {
        this.setState({
            ...this.state,
            controlType: controlType == "Зачет" ? Control.Test : Control.Exam,
        });
    }

    public onChangeDepartment(department: string) {
        this.setState({
            ...this.state,
            department: department,
        });
    }

    public onChangeTeacherName(teacherName: string) {
        this.setState({
            ...this.state,
            teacherName: teacherName,
        });
    }

    public onChangeCourseDescription(courseDescription: string) {
        this.setState({
            ...this.state,
            courseDescription: courseDescription,
        });
    }

    public render() {
        return (
            <>
                <div className={styles.container}>
                    <div className={styles.fontHeader1}>
                        Редактировать курс
                    </div>
                    <div className={styles.helperButtons}>
                        <Link className={styles.linkOverride}
                              to={COURSES_SCREEN_URL + this.state.specialCourseId}>
                            <button className={styles.saveButton} onClick={() => this.editModuleCourse()}>
                                Сохранить
                            </button>
                        </Link>
                        <Link className={styles.linkOverride}
                              to={COURSES_SCREEN_URL + this.state.specialCourseId}>
                            <button className={styles.cancelButton}/>
                        </Link>
                    </div>
                    <div>
                        <label className={styles.label}>
                            Название Курса <span className={styles.required}>*</span>
                        </label>
                        <br/>
                        <input className={styles.input} type={"text"} onChange={(e) => {
                            this.onChangeCourseName(e.target.value)
                        }} required defaultValue={this.state.courseName}/>
                    </div>
                    <div>
                        <label className={styles.label}>
                            Название Кафедры <span className={styles.required}>*</span>
                        </label>
                        <br/>
                        <input className={styles.input} type={"text"} onChange={(e) => {
                            this.onChangeDepartment(e.target.value)
                        }} required defaultValue={this.state.department}/>
                    </div>
                    <div>
                        <label className={styles.label}>
                            Преподаватель <span className={styles.required}>*</span>
                        </label>
                        <br/>
                        <input className={styles.input} type={"text"} onChange={(e) => {
                            this.onChangeTeacherName(e.target.value)
                        }} required defaultValue={this.state.teacherName}/>
                    </div>
                    <div>
                        <label className={styles.label}>
                            Количество зачетных единиц <span className={styles.required}>*</span>
                        </label>
                        <br/>
                        <input className={styles.input} type={"number"} onChange={(e) => {
                            this.onChangeCreditsCount(e.target.value)
                        }} required defaultValue={this.state.creditsCount}/>
                    </div>
                    <div>
                        <label className={styles.label}>
                            Тип отчётности по курсу <span className={styles.required}>*</span>
                        </label>
                        <br/>
                        <select className={styles.select} onChange={(e) => {
                            this.onChangeControlType(e.target.value);
                        }} required>
                            <option selected={this.state.controlType == Control.Test}>
                                Зачет
                            </option>
                            <option selected={this.state.controlType == Control.Exam}>
                                Экзамен
                            </option>
                        </select>
                    </div>
                    <div>
                        <label className={styles.label}>
                            Описание курса <span className={styles.required}>*</span>
                        </label>
                        <br/>
                        <textarea className={styles.inputLong} onChange={(e) => {
                            this.onChangeCourseDescription(e.target.value)
                        }} required defaultValue={this.state.courseDescription}/>
                    </div>
                </div>
            </>
        )
    }

    private async editModuleCourse() {
        await this.props.apis.specialCoursesApi.editModuleSpecialCourse({
            courseId: this.state.specialCourseId,
            courseName: this.state.courseName,
            department: this.state.department,
            teacherName: this.state.teacherName,
            creditsCount: this.state.creditsCount,
            control: this.state.controlType,
            courseDescription: this.state.courseDescription,
        });
    }
}

const reduxConnector = connect(
    (state: RootState) => ({}));

export const EditModuleCourseScreen = reduxConnector(withApis(EditModuleCourseScreenClear));
