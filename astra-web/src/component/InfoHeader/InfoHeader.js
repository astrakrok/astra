import "./InfoHeader.css";

const InfoHeader = ({
                        children,
                        align = "start",
                        className = ""
                    }) => {
    return (
        <div className={`InfoHeader s-hflex-${align}`}>
            <div className={`text ${className}`}>{children}</div>
        </div>
    );
}

export default InfoHeader;
