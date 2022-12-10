import {useState} from "react";
import Button from "../../../Button/Button";
import Input from "../../../Input/Input";
import Spacer from "../../../Spacer/Spacer";
import PopupConsumer from "../../../../context/popup/PopupConsumer";
import LoaderBoundary from "../../../LoaderBoundary/LoaderBoundary";
import IconTitle from "../../../IconTitle/IconTitle";
import MessagePopupBody from "../../../popup-component/MessagePopupBody/MessagePopupBody";
import LinkFooter from "../../../popup-component/LinkFooter/LinkFooter";
import ErrorsArea from "../../../ErrorsArea/ErrorsArea";
import {loadingStatus} from "../../../../constant/loading.status";
import {page} from "../../../../constant/page";
import {signUp} from "../../../../service/auth.service";
import V from "max-validator";
import "./RegisterContent.css";
import {signUpSchema} from "../../../../validation/schema/signUp";
import DisplayBoundary from "../../../DisplayBoundary/DisplayBoundary";
import {message} from "../../../../constant/message";
import SocialAuthButton from "../../../SocialAuth/SocialAuthButton/SocialAuthButton";
import {oauth2Provider} from "../../../../constant/oauth2.provider";

const RegisterContent = () => {
    const [formState, setFormState] = useState({loading: loadingStatus.inProgress, errors: {}});
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [course, setCourse] = useState("");
    const [school, setSchool] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const checkConfirmPassword = (password, confirmPassword) => {
        if (password === confirmPassword) {
            return null;
        }
        return "Паролі не співпадають";
    }

    const checkErrors = (validationResult, signUpData) => {
        const confirmPasswordErrorMessage = validationResult.errors.password ? null : checkConfirmPassword(
            signUpData.password,
            signUpData.confirmPassword
        );
        const confirmPasswordError = confirmPasswordErrorMessage ? {
            confirmPassword: [confirmPasswordErrorMessage]
        } : null;
        if (validationResult.hasError) {
            return {
                ...validationResult.errors,
                ...confirmPasswordError
            };
        }
        return confirmPasswordError;
    }

    const handleSignUp = async setPopupState => {
        setFormState({
            loading: loadingStatus.loading,
            errors: {}
        });
        const signUpData = {
            name,
            surname,
            course,
            school: school === "" ? null : school,
            email,
            password,
            confirmPassword
        };
        
        const validationResult = V.validate(signUpData, signUpSchema);
        const errors = checkErrors(validationResult, signUpData);
        if (errors) {
            setFormState({
                loading: loadingStatus.inProgress,
                errors: errors
            });
            return;
        }
        const data = await signUp(signUpData);

        if (!data.id) {
            setFormState({
                loading: loadingStatus.inProgress,
                errors: data.errors
            });
            return;
        }
        setPopupState({
            bodyGetter: () => <MessagePopupBody message={message.signUpSucceed} />,
            footerGetter: setPopupState => <LinkFooter title="Увійти" to={page.login} setPopupState={setPopupState} />
        });
        setFormState({
            loading: loadingStatus.completed,
            errors: {}
        })
    }

    return (
        <div className="RegisterContent optimal register-form">
            <div className="s-vflex m-hflex-center">
                <div className="s-vflex equal-flex">
                    <Input type="email" className="full-width" placeholder="E-mail" value={email}
                           onChange={event => setEmail(event.target.value)}/>
                    <ErrorsArea errors={formState.errors.email}/>
                </div>
            </div>
            <Spacer height={20}/>
            <div className="s-vflex m-hflex-center">
                <div className="s-vflex equal-flex">
                    <Input placeholder="Прізвище" className="full-width" value={surname}
                           onChange={event => setSurname(event.target.value)}/>
                    <ErrorsArea errors={formState.errors.surname}/>
                </div>
                <Spacer width={20}/>
                <div className="s-vflex equal-flex">
                    <Input placeholder="Ім'я" className="full-width" value={name}
                           onChange={event => setName(event.target.value)}/>
                    <ErrorsArea errors={formState.errors.name}/>
                </div>
            </div>
            <Spacer height={20}/>
            <div className="s-vflex m-hflex-center">
                <div className="s-vflex equal-flex">
                    <Input type="number" min="1" max="6" className="full-width" placeholder="Курс"
                           value={course} onChange={event => setCourse(event.target.value)}/>
                    <ErrorsArea errors={formState.errors.course}/>
                </div>
                <Spacer width={20}/>
                <div className="s-vflex equal-flex">
                    <Input placeholder="Навчальний заклад" className="full-width" value={school}
                           onChange={event => setSchool(event.target.value)}/>
                    <ErrorsArea errors={formState.errors.school}/>
                </div>
            </div>
            <Spacer height={20}/>
            <div className="s-vflex m-hflex-center">
                <div className="s-vflex equal-flex">
                    <Input type="password" placeholder="Пароль" className="full-width"
                           value={password} onChange={event => setPassword(event.target.value)}/>
                    <ErrorsArea errors={formState.errors.password}/>
                </div>
                <Spacer width={20}/>
                <div className="s-vflex equal-flex">
                    <Input type="password" placeholder="Повторити пароль" className="full-width"
                           value={confirmPassword} onChange={event => setConfirmPassword(event.target.value)}/>
                </div>
            </div>

            <Spacer height={20}/>

            <ErrorsArea errors={formState.errors.confirmPassword}/>
            <div className="button s-hflex-center">
                <div className="s-vflex">
                    <LoaderBoundary condition={formState.loading === loadingStatus.loading} size="small"
                                    className="s-hflex-center">
                        {
                            formState.loading === loadingStatus.inProgress ? (
                                <PopupConsumer>
                                    {
                                        ({setPopupState}) => (
                                            <div className="s-vflex full-width">
                                                <Button onClick={() => handleSignUp(setPopupState)}
                                                        className="full-width">
                                                    Зареєструватися
                                                </Button>
                                                <DisplayBoundary condition={formState.errors.global}>
                                                    <Spacer height={20}/>
                                                    <ErrorsArea errors={formState.errors.global}/>
                                                </DisplayBoundary>
                                            </div>
                                        )
                                    }
                                </PopupConsumer>
                            ) : (
                                <div className="s-hflex-center">
                                    <IconTitle className="completed-icon" icon="check" size="medium"/>
                                </div>
                            )
                        }
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
        </div>
    );
}

export default RegisterContent;
