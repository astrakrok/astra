import "./Badge.css";

const Badge = ({
    type = "secondary",
    children
}) => {
    return (
        <div className="Badge">
            <div className={`wrapper ${type}`}>
                {children}
            </div>
        </div>
    );
}

export default Badge;
