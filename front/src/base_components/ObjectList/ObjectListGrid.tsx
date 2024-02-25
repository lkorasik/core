import styles from "./ObjectListGrid.module.css";

interface Props {
    cards: Array<JSX.Element>
}

export function ObjectListGrid(props: Props) {
    return (
        <>
            <div className={styles.grid_container}>
                {props.cards}
            </div>
        </>
    )
}
