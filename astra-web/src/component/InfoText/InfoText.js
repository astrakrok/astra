import "./InfoText.css";

const InfoText = props => {
    const {
        className = "",
        children,
        ...otherProps
    } = props;

    return (
        <p className={`InfoText ${className}`} {...otherProps}>{children}</p>
    );
}

export default InfoText;
