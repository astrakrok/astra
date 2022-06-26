import './Star.css';

export const Star = ({title}) => {
    return (
        <div className="Star">
            <div className="content clickable">{title}</div>
        </div>
    );
}