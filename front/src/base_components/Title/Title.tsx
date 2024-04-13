import styles from "./Title.module.css";

interface Props {
    children?: string
}

export function Title(props: Props) {
    return (
        <>
            <div className={styles.title}>
                {props.children}
            </div>
        </>
    )
}