import "./SocialLink.css";

export const SocialLink = ({social}) => {
    return (
        <div className="SocialLink z-depth-1">
            <a href={social.url}>
                <img src={social.iconUrl} alt={social.url} />
            </a>
        </div>
    );
}
