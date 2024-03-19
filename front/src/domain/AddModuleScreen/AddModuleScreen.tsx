import styles from "./AddModuleScreen.module.css";
import {IAllApisProp, withApis} from "../../apis/ApiBase/ApiProvider";
import {connect, ConnectedProps} from "react-redux";
import {SemesterDto} from "../../apis/api/recommendation/SemesterDto";
import React from "react";
import {RootState} from "../../index";
import {SpecialCourse} from "../../apis/dto/SpecialCourse";
import Select, {MultiValue} from "react-select";
import {Link} from "react-router-dom";
import {MODULE_COURSES_SCREEN_URL, MODULES_SCREEN_URL} from "../App/App";

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
        const specialCourses = await this.props.apis.specialCoursesApi.getAllCourses();
        this.setState({
            specialCoursesForDisplay: specialCourses,
            isLoadingCourses: false,
            specialCoursesIdsForNewModule: this.state.specialCoursesIdsForNewModule,
            moduleName: this.state.moduleName,
        });
    }

    private async createEducationalModule() {
        await this.props.apis.educationalModulesApi.createModule({
            moduleName: this.state.moduleName,
            coursesIds: this.state.specialCoursesIdsForNewModule,
        });
    }

    public onChangeModuleName(moduleName: string) {
        this.setState({
            ...this.state,
            moduleName: moduleName,
        });
    }

    public onChangeSpecialCoursesOption(e: MultiValue<{value: string, label: string}>) {
        var specialCoursesIds = e.map(v => v.value)
        this.setState({
            ...this.state,
            specialCoursesIdsForNewModule: specialCoursesIds,
        });
    }

    public render() {
        const specialCourses: { value: string, label: string }[] = [];
        this.state.specialCoursesForDisplay
            .sort((a, b) => a.name > b.name ? 1 : -1)
            .filter(course => course.educationalModuleId == null)
            .forEach(course => specialCourses.push({value: course.id, label: course.name}));

        return (
            <>
                <div id={styles.container}>
                    <div className={styles.fontHeader1}>
                        Добавить модуль
                    </div>
                    <div id={styles.helperButtons}>
                        <Link className={styles.linkOverride} to={MODULES_SCREEN_URL}>
                            <button className={styles.saveButton} onClick={() => this.createEducationalModule()}>
                                Сохранить
                            </button>
                        </Link>
                        <Link className={styles.linkOverride}
                              to={MODULES_SCREEN_URL}>
                            <button className={styles.cancelButton}/>
                        </Link>
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
                    <div>
                        <label className={styles.label}>
                            Спецкурсы в Модуле <span className={styles.required}>*</span>
                        </label>
                        <Select className={styles.select} defaultValue={specialCourses[0]} options={specialCourses}
                                isMulti={true}
                                onChange={(e) => {
                                    this.onChangeSpecialCoursesOption(e)
                                }}/>
                        <br/>
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
