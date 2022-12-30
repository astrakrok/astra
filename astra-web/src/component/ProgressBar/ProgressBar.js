import "./ProgressBar.css";

const ProgressBar = ({
    title,
    progress,
    children
}) => {
    return (
        <div className="ProgressBar s-vflex-center equal-flex">
            <div className="s-hflex content full-width full-height">
                <div className="title s-vflex-center equal-flex">
                    {title}
                </div>
                <div className="percentage s-hflex">
                    {children}
                </div>
            </div>
            <div className="filler" style={{width: progress + "%"}} />
        </div>
    );
}

export default ProgressBar;
