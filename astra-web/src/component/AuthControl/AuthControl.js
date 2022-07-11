import { page } from "../../constant/page";
import Ref from "../Ref/Ref";
import './AuthControl.css';

const AuthControl = () => {
    return (
        <div className="AuthControl s-hflex-center">
            <Ref className="auth-link s-vflex-center" to={page.login}>Увійти</Ref>
            <Ref className="auth-link s-vflex-center" to={page.register}>Зареєструватися</Ref>
        </div>
    );
}

export default AuthControl;
