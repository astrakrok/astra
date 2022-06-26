import "./Avatar.css";

export const Avatar = ({url}) => {
    return (
        <div className="Avatar border50p z-depth-2" style={{backgroundImage: `url(${url})`}} />
    );
}