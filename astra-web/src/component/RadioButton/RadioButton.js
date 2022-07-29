import "./RadioButton.css";

const RadioButton = (props) => {
    const {
        className = "",
        contentClassName = "",
        disabled = false,
        children,
        ...otherProps
    } = props;

    return (
        <label className={`RadioButton full-width ${className}`} data-disabled={disabled}>
            <input className="with-gap" type="radio" disabled={disabled} {...otherProps}/>
            <span className={contentClassName}>
                {children}
            </span>
        </label>
    );
}

export default RadioButton;
