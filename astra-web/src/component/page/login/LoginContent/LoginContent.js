import {useState} from "react";
import Button from "../../../Button/Button";
import Input from "../../../Input/Input";
import ResponsiveColumns from "../../../ResponsiveColumns/ResponsiveColumns";
import Spacer from "../../../Spacer/Spacer";
import LoaderBoundary from "../../../LoaderBoundary/LoaderBoundary";
import {Navigate, useNavigate} from "react-router-dom";
import {loginStatus} from "../../../../constant/login.status";
import {page} from "../../../../constant/page";
import "./LoginContent.css";
import {login} from "../../../../service/auth.service";

const LoginContent = ({
    onSuccess = () => {}
}) => {
    const navigate = useNavigate();
    
    const [status, setStatus] = useState(loginStatus.inProgress);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = async () => {
        setStatus(loginStatus.inProgress);
        const tokenResponse = await login({email, password});
        await onSuccess(tokenResponse);
        navigate(page.home);
    }

    const getFirstColumn = () => {
        return (
            <div className="optimal login-form">
                <Input placeholder="E-mail" value={email} onChange={event => setEmail(event.target.value)} />
                <Input type="password" className="browser-default" placeholder="Пароль" value={password} onChange={event => setPassword(event.target.value)} />
                <div className="button center">
                    <LoaderBoundary condition={status === loginStatus.processing} size="small">
                        <Button onClick={handleLogin}>
                            Увійти
                        </Button>
                    </LoaderBoundary>
                </div>
            </div>
        );
    };

    const getSecondColumn = () => {
        return (
            <div className="s-vflex-center full-height">
                <Button className="full-width" isFilled={true}>Продовжити з Google</Button>
                <Spacer height="25" />
                <Button className="full-width" isFilled={true}>Продовжити з Facebook</Button>
                <Spacer height="25" />
                <Button className="full-width" isFilled={true}>Продовжити з Instagram</Button>
            </div>
        );
    };

    return (
        <>
            {
                status === loginStatus.loggedIn ? (
                    <Navigate to={page.home} />
                ) : (
                    <ResponsiveColumns
                        firstColumn={getFirstColumn()}
                        secondColumn={getSecondColumn()}
                        isSeparated={true} />
                )
            }
        </>
    );
}

export default LoginContent;
