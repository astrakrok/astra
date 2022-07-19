import "./Badge.css";

const Badge = ({children}) => {
    return (
        <div className="Badge">
            <div className="wrapper">
                {children}
            </div>
        </div>
    );
}

export default Badge;
