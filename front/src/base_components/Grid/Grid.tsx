import styles from "./Grid.module.css";

interface Props {
    cards: Array<JSX.Element>
}

export function Grid(props: Props) {
    return (
        <>
            <div className={styles.grid_container}>
                {props.cards}
            </div>
        </>
    )
}