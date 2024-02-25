import React from "react";
import "./Loader.css"

export class Loader extends React.Component {
    public render() {
        return (
            <div className={"lds-ring"}>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
            </div>
        );
    }
}
