import {Link} from "react-router-dom";
import "./Button.css";

const Button = props => {
    const {
        isFilled = false,
        to = null,
        disabled = false,
        ...other
    } = props;

    return (
        <div className={`Button${isFilled ? " filled" : ""}${disabled ? " disabled" : ""}`}>
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
