import {useState} from "react";
import Button from "../../../Button/Button";
import Input from "../../../Input/Input";
import ResponsiveColumns from "../../../ResponsiveColumns/ResponsiveColumns";
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
import {signUpSchema} from "../../../../validation/scheme/signUp";
import DisplayBoundary from "../../../DisplayBoundary/DisplayBoundary";
import {message} from "../../../../constant/message";

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
            school,
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

    const getFirstColumn = () => {
        return (
            <div className="optimal register-form">
                <Input placeholder="Ім'я" value={name} onChange={event => setName(event.target.value)} />
                <ErrorsArea errors={formState.errors.name} />
                <Spacer height={20} />
                <Input placeholder="Прізвище" value={surname} onChange={event => setSurname(event.target.value)} />
                <ErrorsArea errors={formState.errors.surname} />
                <Spacer height={20} />
                <Input placeholder="Курс" value={course} onChange={event => setCourse(event.target.value)} />
                <Spacer height={20} />
                <Input placeholder="Навчальний заклад" value={school} onChange={event => setSchool(event.target.value)} />
                <Spacer height={20} />
                <Input type="email" className="browser-default" placeholder="E-mail" value={email} onChange={event => setEmail(event.target.value)} />
                <ErrorsArea errors={formState.errors.email} />
                <Spacer height={20} />
                <Input type="password" className="browser-default" placeholder="Пароль" value={password} onChange={event => setPassword(event.target.value)} />
                <ErrorsArea errors={formState.errors.password} />
                <Spacer height={20} />
                <Input type="password" className="browser-default" placeholder="Повторити пароль" value={confirmPassword} onChange={event => setConfirmPassword(event.target.value)} />
                <ErrorsArea errors={formState.errors.confirmPassword} />
                <div className="button center">
                    <LoaderBoundary condition={formState.loading === loadingStatus.loading} size="small">
                        {
                            formState.loading === loadingStatus.inProgress ? (
                                <PopupConsumer>
                                    {
                                        ({setPopupState}) => (
                                            <div className="s-vflex">
                                                <div className="s-hflex-center">
                                                    <Button onClick={() => handleSignUp(setPopupState)}>
                                                        Зареєструватися
                                                    </Button>
                                                </div>
                                                <DisplayBoundary condition={formState.errors.global}>
                                                    <Spacer height={20} />
                                                    <ErrorsArea errors={formState.errors.global} />
                                                </DisplayBoundary>
                                            </div>
                                        )
                                    }
                                </PopupConsumer>
                            ) : (
                                <div className="s-hflex-center">
                                    <IconTitle className="completed-icon" icon="check" size="medium" />
                                </div>
                            )
                        }
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
        <ResponsiveColumns
            firstColumn={getFirstColumn()}
            secondColumn={getSecondColumn()}
            isSeparated={true} />
    );
}

export default RegisterContent;
