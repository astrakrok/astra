import {useContext, useEffect} from "react";
import {useNavigate, useSearchParams} from "react-router-dom";
import {page} from "../../../../constant/page";
import {googleLogin} from "../../../../service/auth.service";
import Preloader from "../../../Preloader/Preloader";
import "./GoogleCallbackPage.css";
import AuthContext from "../../../../context/auth/AuthContext";

const GoogleCallbackPage = () => {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const {setUserData} = useContext(AuthContext);

    useEffect(() => {
        const login = async () => {
            const code = searchParams.get("code");
            const userData = await googleLogin(code);
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

export default GoogleCallbackPage;
