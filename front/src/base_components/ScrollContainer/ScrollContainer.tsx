import React, {CSSProperties, ReactNode} from "react";
import "./hiddenScroll.css";

interface Props {
    children: ReactNode | ReactNode[];
    className?: string
}

export class ScrollContainer extends React.Component<Props> {
    public render() {
        const style: CSSProperties = {
            overflowY: "scroll",
            overflowX: "hidden",
        };

        const classNames = this.props.className ? this.props.className + " hiddenScroll" : "hiddenScroll";
        return (
            <div className={classNames} style={style}>
                {this.props.children}
            </div>
        );
    }
}
