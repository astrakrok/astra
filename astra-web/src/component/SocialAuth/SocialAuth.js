import {getGoogleLoginUrl} from "../../service/auth.service";
import Button from "../Button/Button";
import Spacer from "../Spacer/Spacer";
import "./SocialAuth.css";
import SocialAuthButton from "./SocialAuthButton/SocialAuthButton";

export const SocialAuth = () => {
    return (
        <div className="s-vflex-center full-height">
            <SocialAuthButton urlFetcher={getGoogleLoginUrl}>
                Продовжити з Google
            </SocialAuthButton>
            <Spacer height="25"/>
            <Button className="full-width" isFilled={true}>Продовжити з Facebook</Button>
            <Spacer height="25"/>
            <Button className="full-width" isFilled={true}>Продовжити з Instagram</Button>
        </div>
    );
}

export default SocialAuth;
