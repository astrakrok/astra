import "./ResponsiveColumns.css";

const ResponsiveColumns = ({
    firstColumn = <></>,
    secondColumn = <></>,
    isSeparated = false,
    className = "",
    firstClassName = "",
    secondClassName = ""
}) => {
    return (
        <div className={`ResponsiveColumns s-vflex m-hflex ${className}`}>
            <div className={`first equal-flex s-hflex-center m-no-width ${firstClassName}`}>
                {firstColumn}
            </div>
            
            {
                isSeparated ? (
                    <>
                        <div className="v-separator hide-on-small-only" />
                        <div className="h-separator hide-on-med-and-up" />
                    </>
                ) : null
            }
            
            <div className={`second equal-flex s-hflex-center m-no-width ${secondClassName}`}>
                {secondColumn}
            </div>
        </div>
    );
}

export default ResponsiveColumns;
