import { CloseButton } from "../CrudButtons/CloseButton/CloseButton";
import { DeleteButton } from "../CrudButtons/DeleteButton/DeleteButton";
import { EditButton } from "../CrudButtons/EditButton/EditButton";
import { SaveButton } from "../CrudButtons/SaveButton/SaveButton";
import { Title } from "../Title/Title";
import styles from "./Toolbar.module.css";

interface Props {
    children?: string
}

export function Toolbar(props: Props) {
    return (
        <>
            <div className={styles.toolbar}>
                <Title>{props.children}</Title>
                <div className={styles.actions}>
                    <SaveButton to="a" />
                    <EditButton to="a" />
                    <DeleteButton to="s" />
                    <CloseButton to="sd" />
                </div>
            </div>
        </>
    )
}