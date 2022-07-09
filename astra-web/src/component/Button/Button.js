import { Link } from "react-router-dom";
import "./Button.css";

const Button = props => {
    const {
        isFilled = false,
        to = null,
        ...other
    } = props;

    return (
        <div className={`Button${isFilled ? " filled" : ""}`}>
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
