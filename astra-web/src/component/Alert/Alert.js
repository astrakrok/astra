import "./Alert.css";

const Alert = ({
    type = "success",
    children
}) => {
    return (
        <div className={`Alert ${type}`}>
            {children}
        </div>
    );
}

export default Alert;
