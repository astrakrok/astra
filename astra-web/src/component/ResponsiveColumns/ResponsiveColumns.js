import "./ResponsiveColumns.css";

export const ResponsiveColumns = ({
    firstColumn = <></>,
    secondColumn = <></>,
    isSeparated = false,
    className = ""
}) => {
    return (
        <div className={`ResponsiveColumns s-vflex m-hflex ${className}`}>
            <div className="first equal-flex s-hflex-center m-no-width">
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
            
            <div className="second equal-flex s-hflex-center m-no-width">
                {secondColumn}
            </div>
        </div>
    );
}
