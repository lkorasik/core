import styles from "./DownloadButton.module.css";

interface Props {
    title?: string
    onClick?: () => void
}

export function DownloadButton(props: Props) {
    return (
        <>
            <button title={props.title} className={styles.downloadButton} onClick={props.onClick}/>
        </>
    )
}