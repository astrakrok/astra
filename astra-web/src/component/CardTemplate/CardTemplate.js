import "./CardTemplate.css";

const CardTemplate = ({
    className = "",
    heading = <></>,
    content = <></>
}) => {
    return (
        <div className={`CardTemplate s-vflex z-depth-2 ${className}`}>
            <div className="s-vflex heading">
                {heading}
            </div>
            <div className="content">
                {content}
            </div>
        </div>
    );
}

export default CardTemplate;
