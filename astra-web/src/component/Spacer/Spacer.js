const Spacer = ({
    height = 0,
    width = 0,
    className = ""
}) => {
    return (
        <div className={className} style={{
            width: width + "px",
            height: height + "px"
        }} />
    );
}

export default Spacer;
