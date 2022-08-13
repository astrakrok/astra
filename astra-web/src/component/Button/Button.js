import {useNavigate} from "react-router-dom";
import "./Button.css";

const Button = props => {
    const {
        type = "primary",
        isFilled = false,
        to = null,
        disabled = false,
        onClick = () => {
        },
        isSubmit = false,
        ...other
    } = props;

    const navigate = useNavigate();

    const handleClick = async () => {
        await onClick();
        if (to != null) {
            navigate(to);
        }
    }

    return (
        <div className={`Button${isFilled ? " filled" : ""}${disabled ? " disabled" : ""} ${type}`}>
            {
                <button {...other} onClick={handleClick} type={`${isSubmit ? "submit" : "button"}`}>
                    {props.children}
                </button>
            }
        </div>
    );
}

export default Button;
