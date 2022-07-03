import "./Dropdown.css";
import M from "materialize-css";
import { useEffect } from "react";

export const Dropdown = ({
    id,
    trigger = <></>,
    content = <></>
}) => {
    useEffect(() => {
        const element = document.querySelectorAll(`[data-target='${id}']`);
        M.Dropdown.init(element, {
            alignment: "right",
            coverTrigger: false,
            inDuration: 300,
            outDuration: 300,
            constrainWidth: false
        });
    });

    return (
        <>
            <div className="dropdown-trigger" data-target={id}>
                {trigger}
            </div>

            <div id={id} className="dropdown-content">
                {content}
            </div>
        </>
    );
}
