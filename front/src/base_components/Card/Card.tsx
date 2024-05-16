import styles from "./Card.module.css";
import {Link} from "react-router-dom";

interface Props {
    text: string,
    link: string,
    type?: string,
    paramNames?: string[],
    paramsValues?: string[],
    badge?: string
}

export function Card(props: Props) {
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

    const getBadgeClass = () => {
        if (props.badge) {
            return styles.grid_badge_show;
        } else {
            return styles.grid_badge_hide;
        }
    }

    return (
        <>
            <Link to={props.link} className={styles.grid_link} onClick={onClick}>
                <div className={styles.grid_card}>
                    <span className={styles.grid_card_text}>{props.text}</span>
                    <img
                        className={styles.grid_card_img}
                        src={process.env.PUBLIC_URL + "/gradient-red-1.png"}
                        alt={""}
                    />
                    <div className={getBadgeClass()}>{props.badge}</div>
                </div>
            </Link>
        </>
    )
}
