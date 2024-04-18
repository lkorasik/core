import { Link, useParams } from "react-router-dom";
import { Card } from "../../../base_components/Card/Card";
import { Container } from "../../../base_components/Container/Container";
import { Grid } from "../../../base_components/Grid/Grid";
import { Toolbar } from "../../../base_components/Toolbar/Toolbar";
import { useEffect, useState } from "react";
import { useApis } from "../../../apis/ApiBase/ApiProvider";
import { ProgramIdDto } from "../../../apis/api/programs/ProgramIdDto";
import { GetGroupDto } from "../../../apis/api/groups/GetGroupDto";
import { AddButton } from "../../../base_components/AddButton/AddButton";
import { GroupDto } from "../../../apis/api/groups/GroupDto";

export const PROGRAM_ID_KEY = "ProgramId";

export function EducationalProgramDetailsScreen() {
    const [educationalProgramName, setEducationalProgramName] = useState<string>();
    const [groups, setGroups] = useState<GroupDto[]>([]);

    const { educationalProgramId } = useParams();
    const api = useApis();

    useEffect(() => {
        const loadModule = async () => { 
            const request = { id: educationalProgramId } as ProgramIdDto 
            const response = await api.educationalProgramsApi.getEducationalProgramById(request);

            setEducationalProgramName(response.title);
        };
        loadModule().catch(console.error);

        const loadGroups = async () => {
            const request = { programId: educationalProgramId } as GetGroupDto
            const response = await api.groupsApi.getGroupsForProgram(request);

            setGroups(response)
        }
        loadGroups().catch(console.error);
    }, [])

    const renderCards = () => {
        return groups.map(x => <Card
            link={"/administrator/educational_program/"}
            text={x.number}
            type={"Group"}
            paramNames={["Id", "Name"]}
            paramsValues={[x.id, x.number]} />)
    }

    return (
        <>
            <Container>
                <Toolbar title={educationalProgramName!}>{educationalProgramName}</Toolbar>
                <Grid cards={renderCards()} />
                <Link to={"/administrator/group/add"} onClick={() => {
                    localStorage.setItem(PROGRAM_ID_KEY, educationalProgramId!);
                }}>
                    <AddButton />
                </Link>
            </Container>
        </>
    )
}