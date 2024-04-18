import { useEffect, useState } from "react";
import { Container } from "../../base_components/Container/Container";
import { CloseButton } from "../../base_components/CrudButtons/CloseButton/CloseButton";
import { SaveButton } from "../../base_components/CrudButtons/SaveButton/SaveButton";
import { Flex } from "../../base_components/Flex/Flex";
import { Input } from "../../base_components/Input/Input";
import { Toolbar } from "../../base_components/Toolbar/Toolbar";
import { ADMINISTRATOR } from "../App/App";
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { CreateGroupDto } from "../../apis/api/groups/CreateGroupDto";
import { GetGroupDto } from "../../apis/api/groups/GetGroupDto";
import { PROGRAM_ID_KEY } from "../EducationalProgramScreen/EducationalProgramDetailsScreen/EducationalProgramDetailsScreen";

export function AddGroupScreen() {
    const [groupNumber, setGroupNumber] = useState("");
    const [startYear, setStartYear] = useState(2023)
    const [programId, setProgramId] = useState<string>();

    const api = useApis()

    const save = async () => {
        const programId = localStorage.getItem(PROGRAM_ID_KEY);
        const group = {
            number: groupNumber,
            year: startYear,
            programId: programId
        } as CreateGroupDto
        await api.groupsApi.createGroup(group)
    }

    return (
        <Container>
            <Toolbar title="Добавить академическую группу">
                <SaveButton to={ADMINISTRATOR + "/educational_program"} onClick={() => save()} />
                <CloseButton to={ADMINISTRATOR + "/educational_program"} />
            </Toolbar>
            <Flex direction="column">
                <span>Номер группы</span>
                <Input type="text" placeholder="Text" onChange={(e) => {setGroupNumber(e.target.value)}} isRequired={false} />
                <span>Год начала учебы (год, когда проходит первый семестр)</span>
                <Input type="number" placeholder={"Год"} onChange={(e) => {setStartYear(e.target.valueAsNumber)}} isRequired={false} />
            </Flex>
        </Container>
    )
}