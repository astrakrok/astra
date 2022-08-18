import {useContext, useEffect} from "react";
import {useNavigate, useSearchParams} from "react-router-dom";
import {page} from "../../../constant/page";
import {oauth2Login} from "../../../service/auth.service";
import Preloader from "../../Preloader/Preloader";
import "./OAuth2CallbackPage.css";
import AuthContext from "../../../context/auth/AuthContext";

const OAuth2CallbackPage = ({
                                providerName
                            }) => {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const {setUserData} = useContext(AuthContext);

    useEffect(() => {
        const login = async () => {
            const code = searchParams.get("code");
            const userData = await oauth2Login(providerName, {
                code: code,
                redirectUri: window.location.href.split("?")[0]
            });
            setUserData(userData.user);
            navigate(page.home);
        }

        login();
    }, []);

    return (
        <div className="s-hflex-center">
            <Preloader/>
        </div>
    );
}

export default OAuth2CallbackPage;
