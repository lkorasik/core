import styles from "./Table.module.css"

export interface Props {
    columnTitles: string[]
    rows: string[][]
}

export function Table(props: Props) {
    const renderTitle = () => {
        return props.columnTitles.map(x => <th>{x}</th>)
    }

    const renderBody = () => {
        return props.rows.map(row => {
            return <tr>{row.map(cell => <td>{cell}</td>)}</tr>
        })
    }

    return (
        <div>
            <table className={styles.table}>
                <thead>
                    <tr>
                        {renderTitle()}
                    </tr>
                </thead>
                <tbody>
                    {renderBody()}
                </tbody>
            </table>
        </div>
    )
}