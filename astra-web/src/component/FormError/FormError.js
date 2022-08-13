import "./FormError.css";

const FormError = props => {
    const {
        message,
        className = "",
        ...otherProps
    } = props;

    return (
        <div className={`FormError ${className}`} {...otherProps}>
            {message}
        </div>
    );
}

export default FormError;
