import "./Input.css";

const Input = props => {
    const {
        inputType = "primary",
        placeholder,
        withLabel = true,
        className = "",
        ...otherProps
    } = props;

    return (
        <div className={`Input s-vflex ${inputType}`}>
            {
                withLabel ? (
                    <label>{placeholder}</label>
                ) : null
            }
            <input type="input" placeholder={placeholder} className={`browser-default ${className}`} {...otherProps} />
        </div>
    );
}

export default Input;
