import {Link} from "react-router-dom";
import "./Button.css";

const Button = props => {
    const {
        type = "primary",
        isFilled = false,
        to = null,
        disabled = false,
        ...other
    } = props;

    return (
        <div className={`Button${isFilled ? " filled" : ""}${disabled ? " disabled" : ""} ${type} ${to == null ? "simple" : "link"}`}>
            {
                to == null ? (
                    <button {...other}>
                        {props.children}
                    </button>
                ) : (
                    <Link to={to} {...other}>
                        {props.children}
                    </Link>
                )
            }
        </div>
    );
}

export default Button;
