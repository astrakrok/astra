import './TextHeader.css';

export const TextHeader = ({text}) => {
    return (
        <div className="TextHeader">
            <p className="text">{text}</p>
        </div>
    );
}