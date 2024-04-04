import React, {CSSProperties} from "react";

interface Props {
    size: number;
    align?: "left" | "right" | "center";
    fontWeight?: "bold";
    color?: string;
    className?: string;
    children?: React.ReactNode | React.ReactNode[];
}

export class Text extends React.Component<Props> {
    public render() {
        const style: CSSProperties = {
            fontSize: this.getRemSize(this.props.size),
            textAlign: this.props.align ?? "left",
            fontWeight: this.props.fontWeight,
            color: this.props.color,
        };
        return (
            <div className={this.props.className} style={style}>
                {this.props.children}
            </div>
        );
    }

    private getRemSize(size: number) {
        return `${size}rem`;
    }
}
