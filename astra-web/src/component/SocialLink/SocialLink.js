import "./SocialLink.css";

export const SocialLink = ({social}) => {
    return (
        <div className="SocialLink">
            <a href={social.url}>
                <img src={social.iconUrl} alt={social.url} />
            </a>
        </div>
    );
}
