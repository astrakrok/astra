import "./Divider.css";

const Divider = ({
    orientation = "horizontal"
}) => {
    return (
        <div className={`Divider ${orientation}`} />
    );
}

export default Divider;
