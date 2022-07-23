import "./Tooltipped.css";

const Tooltipped = ({
    children,
    tooltip,
    className = "",
    position = "right"
}) => {
    return (
        <div className={`Tooltipped ${position} ${className}`} data-text={tooltip}>
            {children}
        </div>
    );
}

export default Tooltipped;
