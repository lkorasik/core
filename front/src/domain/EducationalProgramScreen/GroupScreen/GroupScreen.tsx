import { useParams } from "react-router-dom";
import { Container } from "../../../base_components/Container/Container";
import { Title } from "../../../base_components/Title/Title";
import { useApis } from "../../../apis/ApiBase/ApiProvider";
import { useEffect, useState } from "react";
import { GetGroupIdDto } from "../../../apis/api/groups/GetGroupIdDto";

export function GroupScreen() {
    const [groupNumber, setGroupNumber] = useState<string>();

    const api = useApis();
    const { groupId } = useParams();
    
    useEffect(() => {
        const loadGroup = async () => {
            const request = { groupId: groupId } as GetGroupIdDto;
            const response =  await api.groupsApi.getGroup(request);

            setGroupNumber(response.number);
        }
        loadGroup().catch(console.error);
    }, [])
    
    return (
        <Container>
            <Title>{groupNumber}</Title>
        </Container>
    )
}