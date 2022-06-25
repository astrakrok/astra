import { Link } from "react-router-dom";
import { page } from "../../constant/page";
import './AuthControl.css';

export const AuthControl = () => {
    return (
        <div className="AuthControl s-hflex-center">
            <Link className="auth-link s-vflex-center" to={page.login}>Увійти</Link>
            <Link className="auth-link s-vflex-center" to={page.register}>Зареєструватися</Link>
        </div>
    );
}