import {oauth2Provider} from "../../constant/oauth2.provider";
import Spacer from "../Spacer/Spacer";
import "./SocialAuth.css";
import SocialAuthButton from "./SocialAuthButton/SocialAuthButton";

export const SocialAuth = () => {
    return (
        <div className="SocialAuth s-vflex-center full-height">
            <SocialAuthButton providerName={oauth2Provider.google}>
                Продовжити з Google
            </SocialAuthButton>
            <Spacer height={10}/>
            <div className="lined">
                <Spacer width={10}/>
                або
                <Spacer width={10}/>
            </div>
            <Spacer height={10}/>
            <SocialAuthButton providerName={oauth2Provider.facebook}>
                Продовжити з Facebook
            </SocialAuthButton>
        </div>
    );
}

export default SocialAuth;
