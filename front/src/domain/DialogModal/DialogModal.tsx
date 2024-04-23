import { Button } from "../../base_components/Buttons/Button/Button";
import { Flex } from "../../base_components/Flex/Flex";
import styles from "./DialogModal.module.css";
import { ReactNode, useEffect, useRef, useState } from "react"

interface Props {
    children?: ReactNode | ReactNode[] | Array<JSX.Element>;
    close: () => void;
    onRightClick?: () => void;
    title: string;
    leftButtonTitle?: string;
    rightButtonTitle?: string;
}

export function DialogModal(props: Props) {
    const [show, setShow] = useState(true)

    const getLeftButtonTitle = () => {
        if (props.leftButtonTitle){
            return props.leftButtonTitle;
        } else {
            return "Отмена";
        }
    }

    const getRightButtonTitle = () => {
        if (props.rightButtonTitle){
            return props.rightButtonTitle;
        } else {
            return "Сохранить";
        }
    }

    const render = () => {
        if(show) {
            return (
                <div className={styles.back}>
                    <div className={styles.dialog}>
                        <h3 className={styles.dialog_title}>{props.title}</h3>
                        {props.children}
                        <Flex direction={"row"} justifyContent={"center"}>
                            <Button className={styles.button} onClick={() => props.close()}>
                                {getLeftButtonTitle()}
                            </Button>
                            <Button className={styles.button} onClick={() => {
                                if (props.onRightClick) {
                                    props.onRightClick();
                                }
                                props.close();
                            }}>
                                {getRightButtonTitle()}
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