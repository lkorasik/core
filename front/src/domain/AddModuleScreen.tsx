import { useState } from "react";
import { CloseButton } from "../base_components/Buttons/CrudButtons/CloseButton/CloseButton";
import { SaveButton } from "../base_components/Buttons/CrudButtons/SaveButton/SaveButton";
import { Container } from "../base_components/Container/Container";
import { InputField } from "../base_components/InputField/InputField";
import { Toolbar } from "../base_components/Toolbar/Toolbar";
import { useApis } from "../apis/ApiBase/ApiProvider";
import { CreateModuleDto } from "../apis/api/modules/CreateModuleDto";
import { ADMINISTRATOR, COURESE_AND_MODULES } from "./App/App";

export function AddModuleScreen() {
    const [name, setName] = useState<string>("")

    const api = useApis()

    const save = () => {
        const request: CreateModuleDto = { moduleName: name }
        api.educationalModulesApi.createModule(request)
    }

    return (
        <Container>
            <Toolbar title="Создание модуля">
                <SaveButton to={ADMINISTRATOR + COURESE_AND_MODULES} onClick={save} />
                <CloseButton />
            </Toolbar>
            <InputField isRequired onChange={(e) => setName(e.target.value)}>Название</InputField>
        </Container>
    )
}