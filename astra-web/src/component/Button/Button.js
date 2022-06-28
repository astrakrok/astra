import "./Button.css";

export const Button = props => {
    const {isFilled = false, ...other} = props;

    return (
        <div className={`Button${isFilled ? " filled" : ""}`}>
            <button {...other}>
                {props.children}
            </button>
        </div>
    );
}
