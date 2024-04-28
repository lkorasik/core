import { useEffect, useState } from "react";
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { ModuleDto } from "../../apis/api/modules/ModuleDto";
import { Card } from "../../base_components/Card/Card";
import { Grid } from "../../base_components/Grid/Grid";
import { Link } from "react-router-dom";
import { Title } from "../../base_components/Title/Title";
import { Container } from "../../base_components/Container/Container";
import { AddButton } from "../../base_components/Buttons/AddButton/AddButton";

export function ModulesScreen() {
    const [modules, setModules] = useState<ModuleDto[]>([]);

    const apis = useApis();

    useEffect(() => {
        const loadModules = async () => {
            const modules = await apis.educationalModulesApi.getAllModules();
            setModules(modules);
        }
        loadModules().catch(console.error);
    }, [])

    const renderCard = (x: ModuleDto) => {
        return (
            <Card
                link={"/administrator/module/" + x.id}
                text={x.name}
                type={"EducationalProgram"}
                paramNames={["Id", "Name"]}
                paramsValues={[x.id, x.name]} />
        )
    }

    return (
        <>
            <Container>
                <Title>Курсы и модули</Title>
                <Grid cards={modules.map(x => renderCard(x))}/>
                <Link to={"/administrator/module/add"}>
                    <AddButton />
                </Link>
            </Container>
        </>
    )
}