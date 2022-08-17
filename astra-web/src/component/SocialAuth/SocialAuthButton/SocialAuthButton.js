import {useState} from "react";
import Button from "../../Button/Button";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import "./SocialAuthButton.css";

const SocialAuthButton = ({
                              children,
                              urlFetcher = () => {
                              }
                          }) => {
    const [loading, setLoading] = useState(false);

    const redirect = async () => {
        setLoading(true);
        const result = await urlFetcher();
        window.location.href = result.url;
    }

    return (
        <LoaderBoundary condition={loading} size="small" className="s-hflex-center">
            <Button className="full-width" isFilled={true} onClick={redirect}>
                {children}
            </Button>
        </LoaderBoundary>
    );
}

export default SocialAuthButton;
