import { Button } from "../../base_components/Button/Button";
import { Flex } from "../../base_components/Flex/Flex";
import styles from "./DialogModal.module.css";
import { ReactNode, useEffect, useRef, useState } from "react"

interface Props {
    children?: ReactNode | ReactNode[] | Array<JSX.Element>;
    close: () => void;
    title: string
}

export function DialogModal(props: Props) {
    const [show, setShow] = useState(true)

    const render = () => {
        if(show) {
            return (
                <div className={styles.back}>
                    <div className={styles.dialog}>
                        <h3 className={styles.dialog_title}>{props.title}</h3>
                        {props.children}
                        <Flex direction={"row"} justifyContent={"center"}>
                            <Button className={styles.button} onClick={() => props.close()}>
                                Отмена
                            </Button>
                            <Button className={styles.button} onClick={() => props.close()}>
                                Сохранить
                            </Button>
                        </Flex>
                    </div>
                </div>
            )
        } else {
            return (<></>)
        }
    }

    return (
        <>
        {render()}
        </>
    )
}