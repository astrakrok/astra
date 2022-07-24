import "./InfoHeader.css";

const InfoHeader = ({
    text
}) => {
    return (
        <div className="InfoHeader s-hflex">
            <p className="text">{text}</p>
        </div>
    );
}

export default InfoHeader;
