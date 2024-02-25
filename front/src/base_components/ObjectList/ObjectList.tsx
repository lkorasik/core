import styles from "./ObjectList.module.css";
import {Link} from "react-router-dom";

interface Props {
    courseName: string,
    link: string,
    studentsCount: number,
    type?: string,
    paramNames?: string[],
    paramsValues?: string[],
}

export function ObjectInList(props: Props) {
    const params: Record<string, string> = (props.paramNames ?? []).reduce((acc: Record<string, string>, key: string, index: number) => {
        if (props.paramsValues && props.paramsValues[index]) {
            acc[key] = props.paramsValues[index];
        }
        return acc;
    }, {});

    const onClick = () => {
        if (props.type && props.paramNames && props.paramsValues)
        {
            for (let i = 0; i < props.paramNames?.length; i++) {
                localStorage.setItem(props.type + props.paramNames[i], props.paramsValues[i]);
            }
        }
    }
    return (
        <>
            <Link to={props.link} className={styles.grid_link} onClick={onClick}>
                <div className={styles.grid_card}>
                    <div className={styles.grid_card_text_left_point}>.</div>
                    <span className={styles.grid_card_text_left}>{props.courseName}</span>
                    <span className={styles.grid_card_text_right}>{props.studentsCount}</span>
                </div>
            </Link>
        </>
    )
}
