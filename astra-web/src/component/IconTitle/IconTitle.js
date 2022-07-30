import "./IconTitle.css";

const IconTitle = ({
    icon = "star",
    size = "small",
    title = "",
    className = ""
}) => {
    return (
        <div className={`s-hflex IconTitle ${className}`}>
            <i className={`${size} material-icons`}>{icon}</i>
            <span className="title s-vflex-center">{title}</span>
        </div>
    );
}

export default IconTitle;
