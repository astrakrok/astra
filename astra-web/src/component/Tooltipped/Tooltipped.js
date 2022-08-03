import "./Tooltipped.css";

const Tooltipped = props => {
    const {
        children,
        tooltip,
        className = "",
        position = "right",
        ...otherProps
    } = props;

    return (
        <div className={`Tooltipped ${position} ${className}`} data-text={tooltip} {...otherProps}>
            {children}
        </div>
    );
}

export default Tooltipped;
