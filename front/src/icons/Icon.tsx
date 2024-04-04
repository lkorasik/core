import React, {CSSProperties} from "react";
import {SearchIcon} from "./svg/SearchIcon";
import {TriangleIcon} from "./svg/TriangleIcon";
import {PlusIcon} from "./svg/PlusIcon";
import {MinusIcon} from "./svg/MinusIcon";

export enum IconType {
    SearchIcon = "SearchIcon",
    TriangleIcon = "TriangleIcon",
    PlusIcon = "PlusIcon",
    MinusIcon = "MinusIcon",
}

interface Props {
    type: IconType;
    className?: string;
    upsideDown?: boolean;
    height?: number | string
    width?: number | string
    marginTop?: number,
    marginRight?: number,
    marginLeft?: number,
    marginBottom?: number,
}

export class Icon extends React.Component<Props> {
    public render() {
        const style: CSSProperties = {
            marginTop: this.getRemSize(this.props.marginTop),
            marginRight: this.getRemSize(this.props.marginRight),
            marginBottom: this.getRemSize(this.props.marginBottom),
            marginLeft: this.getRemSize(this.props.marginLeft),
            height: typeof this.props.height === "number"
                ? this.getRemSize(this.props.height)
                : this.props.height,
            width: typeof this.props.width === "number"
                ? this.getRemSize(this.props.width)
                : this.props.width,
            transform: !!this.props.upsideDown ? "rotate(180deg)" : undefined,
        };

        return (
            <div className={this.props.className} style={style}>
                {this.chooseImageComponent()}
            </div>
        );
    }

    private chooseImageComponent() {
        switch (this.props.type) {
            case IconType.SearchIcon:
                return <SearchIcon />;
            case IconType.TriangleIcon:
                return <TriangleIcon />;
            case IconType.PlusIcon:
                return <PlusIcon />;
            case IconType.MinusIcon:
                return <MinusIcon />;
            default:
                throw new Error("No image for specified icon type: " + this.props.type);
        }
    }

    private getRemSize(size?: number) {
        if (!size) {
            return undefined;
        }
        return `${size}rem`;
    }
}
