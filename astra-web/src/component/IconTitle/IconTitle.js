import "./IconTitle.css";

const IconTitle = ({
    icon = "star",
    size = "small",
    title = ""
}) => {
    return (
        <div className="s-hflex IconTitle">
            <i className={`${size} material-icons`}>{icon}</i>
            <span className="title s-vflex-center">{title}</span>
        </div>
    );
}

export default IconTitle;
