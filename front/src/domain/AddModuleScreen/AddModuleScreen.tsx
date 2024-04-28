import styles from "./AddModuleScreen.module.css";
import {IAllApisProp, withApis} from "../../apis/ApiBase/ApiProvider";
import {connect, ConnectedProps} from "react-redux";
import React from "react";
import {RootState} from "../../index";
import {SpecialCourse} from "../../apis/api/course/SpecialCourse";
import Select, {MultiValue} from "react-select";
import {Link} from "react-router-dom";
import { MODULES_SCREEN_URL } from "../App/App";
import { Title } from "../../base_components/Title/Title";
import { CloseButton } from "../../base_components/Buttons/CrudButtons/CloseButton/CloseButton";

interface State {
    specialCoursesForDisplay: SpecialCourse[];
    isLoadingCourses: boolean;
    moduleName: string;
    specialCoursesIdsForNewModule: string[];
}

type Props = IAllApisProp & ConnectedProps<typeof reduxConnector>;

class AddModuleScreenClear extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props)
        this.state = {
            specialCoursesForDisplay: [],
            isLoadingCourses: true,
            moduleName: "",
            specialCoursesIdsForNewModule: [],
        };
    }

    public async componentDidMount() {
        this.setState({
            isLoadingCourses: false,
            specialCoursesIdsForNewModule: this.state.specialCoursesIdsForNewModule,
            moduleName: this.state.moduleName,
        });
    }

    private async createEducationalModule() {
        await this.props.apis.educationalModulesApi.createModule({
            moduleName: this.state.moduleName
        });
    }

    public onChangeModuleName(moduleName: string) {
        this.setState({
            ...this.state,
            moduleName: moduleName,
        });
    }

    public render() {
        const specialCourses: { value: string, label: string }[] = [];
        this.state.specialCoursesForDisplay
            .sort((a, b) => a.name > b.name ? 1 : -1)
            .filter(course => course.moduleId == null)
            .forEach(course => specialCourses.push({value: course.id, label: course.name}));

        return (
            <>
                <div id={styles.container}>
                    <Title>Добавить модуль</Title>
                    <div id={styles.helperButtons}>
                        <Link className={styles.linkOverride} to={MODULES_SCREEN_URL}>
                            <button className={styles.saveButton} onClick={() => this.createEducationalModule()}>
                                Сохранить
                            </button>
                        </Link>
                        <CloseButton />
                    </div>
                    <div>
                        <label className={styles.label}>
                            Название Модуля <span className={styles.required}>*</span>
                        </label>
                        <br/>
                        <input className={styles.input} type={"text"} onChange={(e) => {
                            this.onChangeModuleName(e.target.value)
                        }} required/>
                    </div>
                </div>
            </>
        )
    }
}

const reduxConnector = connect(
    (state: RootState) => ({
        chosenCourses: state.courses.chosenCourses,
    }));

export const AddModuleScreen = reduxConnector(withApis(AddModuleScreenClear));
