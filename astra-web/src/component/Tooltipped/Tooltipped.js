import "./Tooltipped.css";

const Tooltipped = ({
    children,
    tooltip,
    position = "right"
}) => {
    return (
        <div className={`Tooltipped ${position}`} data-text={tooltip}>
            {children}
        </div>
    );
}

export default Tooltipped;
