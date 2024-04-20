import styles from "./Table.module.css"

export interface Props {
    columnTitles: string[]
}

export function Table(props: Props) {
    return (
        <div>
            <table className={styles.table}>
                <tr>
                    <th>Токен</th>
                    <th>Статус</th>
                </tr>
                <tr>
                    <td>A</td>
                    <td>A</td>
                </tr>
            </table>
        </div>
    )
}