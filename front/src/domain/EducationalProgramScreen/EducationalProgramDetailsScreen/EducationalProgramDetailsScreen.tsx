import { useParams } from "react-router-dom";
import { Card } from "../../../base_components/Card/Card";
import { Container } from "../../../base_components/Container/Container";
import { Grid } from "../../../base_components/Grid/Grid";
import { Toolbar } from "../../../base_components/Toolbar/Toolbar";
import { useEffect, useState } from "react";
import { useApis } from "../../../apis/ApiBase/ApiProvider";
import { ProgramIdDto } from "../../../apis/api/programs/ProgramIdDto";

export function EducationalProgramDetailsScreen() {
    const [educationalProgramName, setEducationalProgramName] = useState<string>();

    const { educationalProgramId } = useParams();
    const api = useApis();

    useEffect(() => {
        const loadModule = async () => { 
            const request = { id: educationalProgramId } as ProgramIdDto 
            const response = await api.educationalProgramsApi.getEducationalProgramById(request);

            setEducationalProgramName(response.title);
        };
        loadModule().catch(console.error);
    }, [])

    const renderCard = () => {
        return (
            <Card
                link={"/administrator/educational_program/"}
                text={"МЕНМ-123123"}
                type={"EducationalProgram"}
                paramNames={["Id", "Name"]}
                paramsValues={["a", "a"]} />
        )
    }

    return (
        <>
            <Container>
                <Toolbar>{educationalProgramName}</Toolbar>
                <Grid cards={[renderCard()]} />
            </Container>
        </>
    )
}