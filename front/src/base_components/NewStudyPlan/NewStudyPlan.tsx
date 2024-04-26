import { useEffect, useState } from "react";
import { Cell } from "./Cell/Cell";
import styles from "./NewStudyPlan.module.css";

interface Point {
    x: number,
    y: number
}

export function NewStudyPlan() {
    const [width, setWidth] = useState(0);
    const [height, setHeight] = useState(0);
    const [activeCells, setActiveCells] = useState<number[][]>([])

    useEffect(() => {
        const rows: number[][] = []
        for (let i = 0; i < width; i++) {
            const cells = []
            for (let j = 0; j < height; j++) {
                cells.push(0)
            }
            rows.push(cells)
        }
        setActiveCells(rows)
    }, [width, height])

    const onActivate = (x: number, y: number) => {
        // Увеличиваем счетчики на ячейках, которые лежат определенной горизонтали и на определенной вертикали.
        for (let i = 0; i < width; i++) {
            activeCells[i][y]++;
        }
        for (let i = 0; i < height; i++) {
            if (i != y) {
                activeCells[x][i]++;
            } 
        }
        setActiveCells(activeCells)
    }

    const renderRow = (y: number) => {
        return Array.from(Array(width).keys()).map(index => 
            <Cell 
                x={index} 
                y={y}
                onActivate={(x, y) => onActivate(x, y)} />
            );
    }

    const renderColumns = () => {
        return Array.from(Array(height).keys()).map(index => <tr>{renderRow(index)}</tr>)
    }

    return (
        <>
            <input onChange={(e) => setWidth(parseInt(e.target.value))} />
            <input onChange={(e) => setHeight(parseInt(e.target.value))} />
            <table className={styles.table}>
                {renderColumns()}
            </table>
        </>
    )
}