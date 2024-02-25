import React, {CSSProperties, ReactNode} from "react";

interface Props {
    direction: "row" | "column";
    justifyContent?: "center" | "space-between";
    alignItems?: "center" | "end" | "flex-end" | "flex-start" | "self-end" | "self-start" | "start" | "baseline" | "normal" | "stretch";
    gap?: number | string;
    children?: ReactNode | ReactNode[];
    className?: string;
    onClick?: () => void;
}

export class Flex extends React.Component<Props> {
    public render() {
        const style: CSSProperties = {
            display: "flex",
            flexDirection: this.props.direction,
            justifyContent: this.props.justifyContent,
            alignItems: this.props.alignItems,
            gap: typeof this.props.gap === "number"
                ? this.getRemSize(this.props.gap)
                : this.props.gap,
        };

        return (
            <div className={this.props.className} style={style} onClick={this.props.onClick}>
                {this.props.children}
            </div>
        );
    }

    private getRemSize(size: number) {
        return `${size}rem`;
    }
}
