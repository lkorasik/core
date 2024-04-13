import {Card} from "../../base_components/Card/Card";
import {Grid} from "../../base_components/Grid/Grid";
import styles from "./EducationalProgramScreen.module.css";
import { AddButton } from "../../base_components/AddButton/AddButton";
import { Link } from "react-router-dom";
import { useApis } from "../../apis/ApiBase/ApiProvider";
import { useEffect, useState } from "react";
import { ShortProgramDTO } from "../../apis/api/programs/ShortProgramDTO";

interface Props { }

export function EducationalProgramScreen(props: Props) {
    const [educationalPrograms, setEducationalPrograms] = useState<ShortProgramDTO[]>([]);

    const apis = useApis();

    useEffect(() => {
        const loadEducationalPrograms = async () => {
            const educationalPrograms = await apis.educationalProgramsApi.getAllPrograms();
            setEducationalPrograms(educationalPrograms);
        }
        loadEducationalPrograms().catch(console.error);
    }, [apis.educationalProgramsApi])

    const renderCard = (x: ShortProgramDTO) => {
        return (
            <Card
                link={"/administrator/educational_program/" + x.id}
                text={x.name}
                type={"EducationalProgram"}
                paramNames={["Id", "Name"]}
                paramsValues={[x.id, x.name]} />
        )
    }

    return (
        <>
            <div id={styles.container}>
                <Grid cards={educationalPrograms.map(x => renderCard(x))}/>
            </div>
            <Link to={"/administrator/educational_program/add"}>
                <AddButton>
                </AddButton>
            </Link>
        </>
    )
}
