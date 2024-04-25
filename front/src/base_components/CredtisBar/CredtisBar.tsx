import { Flex } from "../Flex/Flex";
import { Input } from "../Input/Input";
import { NText } from "../NText/NText";
import styles from "./CredtisBar.module.css"

export interface Props {
    firstSemester: number,
    secondSemester: number,
    thirdSemester: number,
    fourthSemester: number
}

export function CredtisBar(props: Props) {
    return (
        <Flex direction="column">
            <NText>Рекомендуемое число зет:</NText>
            <Flex direction="row">
                <Flex direction="row" className={styles.box}>
                    <NText>1 семестр: </NText>
                    <Input />
                </Flex>
                <Flex direction="row" className={styles.box}>
                    <NText>2 семестр: </NText>
                    <Input />
                </Flex>
                <Flex direction="row" className={styles.box}>
                    <NText>3 семестр: </NText>
                    <Input />
                </Flex>
                <Flex direction="row" className={styles.box}>
                    <NText>4 семестр: </NText>
                    <Input />
                </Flex>
            </Flex>
        </Flex>
    )
}