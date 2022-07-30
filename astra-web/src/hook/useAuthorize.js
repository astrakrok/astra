import {useContext} from "react";
import {useNavigate} from "react-router-dom";
import {page} from "../constant/page";
import AuthContext from "../context/auth/AuthContext";
import {clear} from "../handler/token.handler";

const useAuthorize = () => {
    const navigate = useNavigate();
    const {setUserData} = useContext(AuthContext);

    return () => {
        clear();
        navigate(page.login);
        setUserData(null);
    };
}

export default useAuthorize;
