import styles from "./AddModuleCourseScreen.module.css";
import {IAllApisProp, withApis} from "../../apis/ApiBase/ApiProvider";
import {connect, ConnectedProps} from "react-redux";
import React from "react";
import {RootState} from "../../index";
import {Link} from "react-router-dom";
import {MODULE_COURSES_SCREEN_URL} from "../App/App";
import {Control} from "../../apis/api/Control";
import { Title } from "../../base_components/Title/Title";
import { CloseButton } from "../../base_components/Buttons/CrudButtons/CloseButton/CloseButton";

interface State {
    educationalModuleId: string;
    courseName: string;
    department: string;
    teacherName: string;
    creditsCount: number;
    controlType: string;
    courseDescription: string;
}

type Props = IAllApisProp & ConnectedProps<typeof reduxConnector>;

class AddModuleCourseScreenClear extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props)
        this.state = {
            educationalModuleId: "",
            courseName: "",
            department: "",
            teacherName: "",
            creditsCount: 0,
            controlType: "Test",
            courseDescription: "",
        };
    }

    public componentDidMount() {
        const moduleId = localStorage.getItem("EducationalModuleId")
        this.setState({
            ...this.state,
            educationalModuleId: moduleId ? moduleId : "",
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
            controlType: controlType === "Зачет" ? Control.Test : Control.Exam,
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
                <div id={styles.container}>
                    <Title>Добавить курс</Title>
                    <div id={styles.helperButtons}>
                        <Link className={styles.linkOverride}
                              to={MODULE_COURSES_SCREEN_URL + this.state.educationalModuleId}>
                            <button className={styles.saveButton} onClick={() => this.createModuleCourse()}>
                                Сохранить
                            </button>
                        </Link>
                        <CloseButton />
                    </div>
                    <div>
                        <label className={styles.label}>
                            Название Курса <span className={styles.required}>*</span>
                        </label>
                        <br/>
                        <input className={styles.input} type={"text"} onChange={(e) => {
                            this.onChangeCourseName(e.target.value)
                        }} required/>
                    </div>
                    <div>
                        <label className={styles.label}>
                            Название Кафедры <span className={styles.required}>*</span>
                        </label>
                        <br/>
                        <input className={styles.input} type={"text"} onChange={(e) => {
                            this.onChangeDepartment(e.target.value)
                        }} required/>
                    </div>
                    <div>
                        <label className={styles.label}>
                            Преподаватель <span className={styles.required}>*</span>
                        </label>
                        <br/>
                        <input className={styles.input} type={"text"} onChange={(e) => {
                            this.onChangeTeacherName(e.target.value)
                        }} required/>
                    </div>
                    <div>
                        <label className={styles.label}>
                            Количество зачетных единиц <span className={styles.required}>*</span>
                        </label>
                        <br/>
                        <input className={styles.input} type={"number"} onChange={(e) => {
                            this.onChangeCreditsCount(e.target.value)
                        }} required/>
                    </div>
                    <div>
                        <label className={styles.label}>
                            Тип отчётности по курсу <span className={styles.required}>*</span>
                        </label>
                        <br/>
                        <select className={styles.select} onChange={(e) => {
                            this.onChangeControlType(e.target.value)
                        }} required defaultValue="Зачет">
                            <option>
                                Зачет
                            </option>
                            <option>
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
                        }} required/>
                    </div>
                </div>
            </>
        )
    }

    private async createModuleCourse() {
        await this.props.apis.specialCoursesApi.createCourse({
            moduleId: this.state.educationalModuleId,
            name: this.state.courseName,
            department: this.state.department,
            teacher: this.state.teacherName,
            credits: this.state.creditsCount,
            controlType: this.state.controlType,
            description: this.state.courseDescription,
        });
    }
}

const reduxConnector = connect(
    (state: RootState) => ({}));

export const AddModuleCourseScreen = reduxConnector(withApis(AddModuleCourseScreenClear));
