import {Card} from "../../base_components/Card/Card";
import {Grid} from "../../base_components/Grid/Grid";
import styles from "./ModuleCoursesScreen.module.css";
import {AddButton} from "../../base_components/AddButton/AddButton";
import {IAllApisProp, withApis} from "../../apis/ApiBase/ApiProvider";
import {connect, ConnectedProps} from "react-redux";
import {ModuleDto} from "../../apis/api/modules/ModuleDto";
import React from "react";
import {Link} from "react-router-dom";
import {SpecialCourse} from "../../apis/api/course/SpecialCourse";
import {MODULES_SCREEN_URL} from "../App/App";
import { CloseButton } from "../../base_components/CrudButtons/CloseButton/CloseButton";
import { EditButton } from "../../base_components/CrudButtons/EditButton/EditButton";
import { DeleteButton } from "../../base_components/CrudButtons/DeleteButton/DeleteButton";

interface State {
    module?: ModuleDto;
    moduleCourses: SpecialCourse[];
    isLoadingCourses: boolean;
    selectedModuleId: string;
    selectedModuleName: string;
}

type Props = IAllApisProp & ConnectedProps<typeof reduxConnector>;

class ModuleScreenClear extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props)
        this.state = {
            moduleCourses: [],
            isLoadingCourses: true,
            selectedModuleId: "",
            selectedModuleName: "",
        };
    }

    public async componentDidMount() {
        const moduleId = localStorage.getItem("EducationalModuleId");
        const moduleName = localStorage.getItem("EducationalModuleName");

        const moduleCourses = moduleId
            ? await this.props.apis.specialCoursesApi.getEducationalModelCourses({moduleId: moduleId,})
            : [];

        this.setState({
            moduleCourses: moduleCourses,
            isLoadingCourses: false,
            selectedModuleId: moduleId ? moduleId : "",
            selectedModuleName: moduleName ? moduleName : "",
        });
    }

    public render() {
        this.componentDidMount()
        const cards: JSX.Element[] = []
        this.state.moduleCourses.forEach(x =>
            cards.push(<Card link={"/administrator/courses/" + x.id} text={x.name} type={"SpecialCourse"}
                             paramNames={["Id", "Name"]}
                             paramsValues={[x.id, x.name]}/>))

        return (
            <>
                <div className={styles.container}>
                    <div className={styles.fontHeader1}>
                        Модуль: {this.state.selectedModuleName}
                    </div>
                    <div className={styles.helperButtons}>
                        <EditButton to={MODULES_SCREEN_URL} />
                        <DeleteButton 
                            to={MODULES_SCREEN_URL} 
                            onClick={() => this.deleteModule()}/>
                        <CloseButton to={MODULES_SCREEN_URL} />
                    </div>
                    <Grid cards={cards}/>
                </div>
                <Link to={"/administrator/courses/add"}>
                    <AddButton>
                    </AddButton>
                </Link>
            </>
        )
    }

    private async deleteModule() {
        await this.props.apis.educationalModulesApi.deleteModuleById({educationalModuleId: this.state.selectedModuleId});
    }
}

const reduxConnector = connect(
    () => ({
    }));

export const ModuleCoursesScreen = reduxConnector(withApis(ModuleScreenClear));
