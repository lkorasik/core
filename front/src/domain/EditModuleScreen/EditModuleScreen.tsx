import styles from "./EditModuleScreen.module.css";
import {IAllApisProp, withApis} from "../../apis/ApiBase/ApiProvider";
import {connect, ConnectedProps} from "react-redux";
import React from "react";
import {SpecialCourse} from "../../apis/dto/SpecialCourse";
import Select, {MultiValue} from "react-select";
import {Link} from "react-router-dom";
import {MODULES_SCREEN_URL} from "../App/App";
import {ModuleDto} from "../../apis/api/modules/ModuleDto";

interface State {
    specialCoursesForDisplay: SpecialCourse[];
    isLoadingCourses: boolean;
    module?: ModuleDto;
    moduleId: string;
    moduleName: string;
    specialCoursesIdsForNewModule: string[];
}

type Props = IAllApisProp & ConnectedProps<typeof reduxConnector>;

class EditModuleScreenClear extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props)
        this.state = {
            specialCoursesForDisplay: [],
            isLoadingCourses: true,
            module: undefined,
            moduleId: "",
            moduleName: "",
            specialCoursesIdsForNewModule: [],
        };
    }

    public async componentDidMount() {
        const moduleId = localStorage.getItem("EducationalModuleId")
        this.setState({
            ...this.state,
            moduleId: moduleId ? moduleId : "",
        });
        if (moduleId) {
            const module = (await this.props.apis.educationalModulesApi
                .getModulesByIds({modulesIds: new Array(this.state.moduleId)}))[0];
            const specialCourses = await this.props.apis.specialCoursesApi.getAllCourses();
            let alreadyModuleCourses = specialCourses
                .map(course => course.educationalModuleId)
                .filter(courseModuleId => courseModuleId != null && moduleId === courseModuleId);

            this.setState({
                ...this.state,
                specialCoursesForDisplay: specialCourses,
                isLoadingCourses: false,
                specialCoursesIdsForNewModule: alreadyModuleCourses ? alreadyModuleCourses : new Array(),
                module: module,
            });
        }
    }

    public onChangeModuleName(moduleName: string) {
        this.setState({
            ...this.state,
            moduleName: moduleName,
        });
    }

    public onChangeSpecialCoursesOption(e: MultiValue<{ value: string, label: string }>) {
        var specialCoursesIds = e.map(v => v.value)
        this.setState({
            ...this.state,
            specialCoursesIdsForNewModule: specialCoursesIds,
        });
    }

    public render() {
        this.componentDidMount()
        const specialCourses: { value: string, label: string }[] = [];
        this.state.specialCoursesForDisplay
            .sort((a, b) => a.name > b.name ? 1 : -1)
            .filter(course => course.educationalModuleId == null)
            .forEach(course => specialCourses.push({value: course.id, label: course.name}));

        return (
            <>
                <div className={styles.container}>
                    <div className={styles.fontHeader1}>
                        Добавить модуль
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
                        <Select className={styles.select} options={specialCourses}
                                isMulti={true}
                                onChange={(e) => {
                                    this.onChangeSpecialCoursesOption(e)
                                }}/>
                        <br/>
                    </div>
                    <div>
                        <Link to={MODULES_SCREEN_URL}>
                            <button onClick={() => {
                                this.createEducationalModule()
                            }}>Добавить
                            </button>
                        </Link>
                    </div>
                </div>
            </>
        )
    }

    private async createEducationalModule() {
        await this.props.apis.educationalModulesApi.createModule({
            moduleName: this.state.moduleName,
            coursesIds: this.state.specialCoursesIdsForNewModule,
        });
    }
}

const reduxConnector = connect(
    () => ({
    }));

export const EditModuleScreen = reduxConnector(withApis(EditModuleScreenClear));
