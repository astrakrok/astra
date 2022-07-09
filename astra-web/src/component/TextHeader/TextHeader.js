import './TextHeader.css';

export const TextHeader = ({
    text,
    additionalContent = <></>
}) => {
    return (
        <div className="TextHeader s-hflex">
            <p className="text">{text}</p>
            <div className="equal-flex" />
            <div className="s-vflex-end additional-content">
                {additionalContent}
            </div>
        </div>
    );
}
