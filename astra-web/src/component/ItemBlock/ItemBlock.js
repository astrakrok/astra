import "./ItemBlock.css";

const ItemBlock = ({
    className = "",
    children = null,
    onClick = () => {}
}) => {
    return (
        <div className={`ItemBlock ${className}`} onClick={onClick}>
            <div className="content s-vflex-center">
                {children}
            </div>
        </div>
    );
}

export default ItemBlock;
