import "./Badge.css";

const Badge = props => {
    const {
        className,
        wrapperClassName,
        type = "secondary",
        children,
        ...otherProps
    } = props;

    return (
        <div className={`Badge ${className}`} {...otherProps}>
            <div className={`wrapper full-height ${type} ${wrapperClassName}`}>
                {children}
            </div>
        </div>
    );
}

export default Badge;
