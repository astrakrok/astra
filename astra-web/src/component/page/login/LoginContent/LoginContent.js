import {useState} from "react";
import Button from "../../../Button/Button";
import Input from "../../../Input/Input";
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
import Ref from "../../../Ref/Ref";
import SocialAuthButton from "../../../SocialAuth/SocialAuthButton/SocialAuthButton";
import {oauth2Provider} from "../../../../constant/oauth2.provider";

const LoginContent = ({
                          onSuccess = () => {
                          }
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
                bodyGetter: () => <MessagePopupBody
                    message="Під час входу сталася помилка! Перевірте свій E-mail і пароль або спробуйте пізніше"/>
            });
            setStatus(loginStatus.inProgress);
        }
    }

    return (
        <>
            {
                status === loginStatus.loggedIn ? (
                    <Navigate to={page.home}/>
                ) : (
                    <div className="optimal login-form s-hflex-center">
                        <div className="s-vflex">
                            <Input placeholder="E-mail" value={email} onChange={event => setEmail(event.target.value)}/>
                            <ErrorsArea errors={errors.email}/>
                            <Spacer height={20}/>
                            <Input type="password" className="browser-default" placeholder="Пароль" value={password}
                                   onChange={event => setPassword(event.target.value)}/>
                            <ErrorsArea errors={errors.password}/>
                            <div className="s-hflex-end">
                                <Ref className="link" to={page.resetPassword}>Забули пароль?</Ref>
                            </div>
                            <div className="button s-hflex-center">
                                <div className="s-vflex">
                                    <LoaderBoundary condition={status === loginStatus.processing} size="small"
                                                    className="s-hflex-center">
                                        <>
                                            <Spacer height={10}/>
                                            <div>
                                                <PopupConsumer>
                                                    {
                                                        ({setPopupState}) => (
                                                            <Button onClick={() => handleLogin(setPopupState)}
                                                                    className="full-width">
                                                                Увійти
                                                            </Button>
                                                        )
                                                    }
                                                </PopupConsumer>
                                            </div>
                                        </>
                                    </LoaderBoundary>
                                    <Spacer height={10}/>
                                    <div className="lined">
                                        <Spacer width={10}/>
                                        або
                                        <Spacer width={10}/>
                                    </div>
                                    <Spacer height={10}/>
                                    <SocialAuthButton providerName={oauth2Provider.google}>
                                        Продовжити з Google
                                    </SocialAuthButton>
                                </div>
                            </div>
                            <Spacer height={10}/>
                        </div>
                    </div>
                )
            }
        </>
    );
}

export default LoginContent;
