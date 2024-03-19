import {Card} from "../../base_components/Card/Card";
import {Grid} from "../../base_components/Grid/Grid";
import styles from "./ModulesScreen.module.css";
import {AddButton} from "../../base_components/AddButton/AddButton";
import {IAllApisProp, withApis} from "../../apis/ApiBase/ApiProvider";
import {connect, ConnectedProps} from "react-redux";
import {ModuleDto} from "../../apis/api/modules/ModuleDto";
import React from "react";
import {Link} from "react-router-dom";

interface State {
    educationalModules: ModuleDto[];
    isLoadingCourses: boolean;
    modalActive: boolean;
}

type Props = IAllApisProp & ConnectedProps<typeof reduxConnector>;

class ModulesScreenClear extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props)
        this.state = {
            educationalModules: [],
            isLoadingCourses: true,
            modalActive: false,
        };
    }

    public async componentDidMount() {
        const educationalModules = await this.props.apis.educationalModulesApi.getAllModules();
        this.setState({
            ...this.state,
            educationalModules: educationalModules,
            isLoadingCourses: false,
        });
    }

    public render() {
        this.componentDidMount()
        const cards = this.state.educationalModules.map(x => (
            <Card
                link={"/administrator/module/" + x.id}
                text={x.name}
                type={"EducationalModule"}
                paramNames={["Id", "Name"]}
                paramsValues={[x.id, x.name]}
            />
        ));
        return (
            <>
                <div className={styles.container}>
                    <div className={styles.fontHeader1}>
                        Курсы и модули
                    </div>
                    <Grid cards={cards}/>
                </div>
                <Link to={"/administrator/module/add"}>
                    <AddButton>
                    </AddButton>
                </Link>
            </>
        )
    }
}

const reduxConnector = connect(
    () => ({
    }));

export const ModulesScreen = reduxConnector(withApis(ModulesScreenClear));
