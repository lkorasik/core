import {CSSProperties, FC} from "react";
import styles from "./Tabs.module.css";
import {Text} from "../Text/Text";

interface Props {
    elements: string[];
    activeTabIndex: number;
    elementWidth?: string;
    onTabClicked?: (tabIndex: number) => void;
    className?: string;
}

export const Tabs: FC<Props> = (props) => {
    const renderElement = (value: string, index: number) => {
        const className = index === props.activeTabIndex
            ? `${styles.tab} ${styles.activeTab}`
            : styles.tab;
        const style: CSSProperties | undefined = props.elementWidth
            ? { width: props.elementWidth }
            : undefined;
        return (
            <div
                key={value}
                className={className}
                style={style}
                onClick={() => {
                    if (props.onTabClicked) {
                        props.onTabClicked(index)
                    }
                }}
            >
                <Text size={2.5}>
                    {value}
                </Text>
            </div>
        );
    }

    const className = props.className ? `${styles.tabsWrap} ${props.className}` : styles.tabsWrap;
    return (
        <div className={className}>
            {props.elements.map(renderElement)}
        </div>
    );
}
