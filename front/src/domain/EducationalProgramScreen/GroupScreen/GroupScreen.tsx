import { useParams } from "react-router-dom";
import { Container } from "../../../base_components/Container/Container";
import { useApis } from "../../../apis/ApiBase/ApiProvider";
import { useEffect, useState } from "react";
import { GetGroupIdDto } from "../../../apis/api/groups/GetGroupIdDto";
import { Toolbar } from "../../../base_components/Toolbar/Toolbar";
import { CloseButton } from "../../../base_components/CrudButtons/CloseButton/CloseButton";
import { EditButton } from "../../../base_components/CrudButtons/EditButton/EditButton";
import { Table } from "../../../base_components/Table/Table";
import { AddButton } from "../../../base_components/AddButton/AddButton";
import { DialogModal } from "../../DialogModal/DialogModal";
import { Input } from "../../../base_components/Input/Input";
import { GenerateTokenDto } from "../../../apis/api/groups/GenerateTokenDto";

export function GroupScreen() {
    const [groupNumber, setGroupNumber] = useState<string>("");
    const [showDialog, setShowDialog] = useState<boolean>(false);
    const [count, setCount] = useState<number>(0);
    const [tokens, setTokens] = useState<string[]>([]);

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

    const generateTokens = async (count: number) => {
        const request = {
            count: count,
            groupId: groupId
        } as GenerateTokenDto;
        const tokens = await api.groupsApi.generateTokens(request);
        setTokens(tokens);
        console.log(tokens);
    }

    const renderDialog = () => {
        return (
            <DialogModal 
                close={() => setShowDialog(false)} 
                title="Сгенерировать регистрационные токены" 
                rightButtonTitle="Сгенерировать"
                onRightClick={() => generateTokens(count)}>
                <span>Число токенов:</span>
                <Input type="number" placeholder="10" isRequired onChange={(e) => setCount(parseInt(e.target.value))}/>
            </DialogModal>
        )
    }

    const renderTokens = () => {
        return tokens.map(x => [x, "Не активирован"])
    }
    
    return (
        <Container>
            {showDialog && renderDialog()}
            <Toolbar title={groupNumber}>
                <EditButton to="" />
                <CloseButton to="" />
            </Toolbar>
            <Table columnTitles={["Токен", "Статус"]} rows={renderTokens()}/>
            <AddButton onClick={() => setShowDialog(true)} />
        </Container>
    )
}