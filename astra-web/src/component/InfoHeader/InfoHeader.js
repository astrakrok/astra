import "./InfoHeader.css";

const InfoHeader = ({
                        children,
                        className = ""
                    }) => {
    return (
        <div className="InfoHeader s-hflex">
            <div className={`text ${className}`}>{children}</div>
        </div>
    );
}

export default InfoHeader;
