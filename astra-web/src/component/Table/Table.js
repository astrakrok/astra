import "./Table.css";

const Table = ({
                   className = "",
                   type = "secondary",
                   children
               }) => {
    return (
        <table className={`${type} ${className} Table`}>
            {children}
        </table>
    );
}

export default Table;
