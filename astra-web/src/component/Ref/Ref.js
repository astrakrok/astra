import { Link } from "react-router-dom";
import "./Ref.css";

export const Ref = props => {
    const className = "Ref" + (props.className ? ` ${props.className}` : "");
    const link = props.to;
    const canUseReactLink = !link.startsWith("mailto:");

    return (
        canUseReactLink ? (
            <Link {...props} className={className} />
        ) : (
            <a {...props} className={className} href={link}>{props.children}</a>
        )
    );
}
