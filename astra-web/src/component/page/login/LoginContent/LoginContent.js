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
import PopupConsumer from "../../../../context/popup/PopupConsumer";
import MessagePopupBody from "../../../popup-component/MessagePopupBody/MessagePopupBody";
import V from "max-validator";
import {loginSchema} from "../../../../validation/schema/login";
import ErrorsArea from "../../../ErrorsArea/ErrorsArea";

const LoginContent = ({
    onSuccess = () => {}
}) => {
    const navigate = useNavigate();
    
    const [status, setStatus] = useState(loginStatus.inProgress);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [errors, setErrors] = useState({});

    const handleLogin = async setPopupState => {
        setErrors({});
        const data = {email, password};
        const result = V.validate(data, loginSchema);
        if (result.hasError) {
            setErrors(result.errors);
            return;
        }
        
        setStatus(loginStatus.processing);
        const userResponse = await login(data);
        const user = userResponse.user;
        if (user) {
            await onSuccess(user);
            navigate(page.home);
        } else {
            setPopupState({
                bodyGetter: () => <MessagePopupBody message="Під час входу сталася помилка! Перевірте свій E-mail і пароль або спробуйте пізніше" />
            });
            setStatus(loginStatus.inProgress);
        }
    }

    const getFirstColumn = () => {
        return (
            <div className="optimal login-form">
                <Input placeholder="E-mail" value={email} onChange={event => setEmail(event.target.value)} />
                <ErrorsArea errors={errors.email} />
                <Spacer height={20} />
                <Input type="password" className="browser-default" placeholder="Пароль" value={password} onChange={event => setPassword(event.target.value)} />
                <ErrorsArea errors={errors.password} />
                <div className="button center">
                    <LoaderBoundary condition={status === loginStatus.processing} size="small">
                        <PopupConsumer>
                            {
                                ({setPopupState}) => (
                                    <Button onClick={() => handleLogin(setPopupState)}>
                                        Увійти
                                    </Button>
                                )
                            }
                        </PopupConsumer>
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
