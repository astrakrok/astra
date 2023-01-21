import "./Badge.css";

const Badge = props => {
    const {
        className = "",
        wrapperClassName = "",
        type = "secondary",
        children,
        limited = true,
        ...otherProps
    } = props;

    return (
        <div className={`Badge ${className} ${limited ? "limited" : ""}`} {...otherProps}>
            <div className={`wrapper full-height ${type} ${wrapperClassName}`}>
                {children}
            </div>
        </div>
    );
}

export default Badge;
