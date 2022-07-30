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
import {loadingStatus} from "../../../../constant/loading.status";
import {page} from "../../../../constant/page";
import {signUp} from "../../../../service/auth.service";
import "./RegisterContent.css";

const RegisterContent = () => {
    const [status, setStatus] = useState(loadingStatus.inProgress);
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [course, setCourse] = useState("");
    const [school, setSchool] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const handleSignUp = async setPopupState => {
        setStatus(loadingStatus.loading);
        const signUpData = {
            name,
            surname,
            course,
            school,
            email,
            password,
            confirmPassword
        };

        const data = await signUp(signUpData);

        if (data.id) {
            setPopupState({
                bodyGetter: () => <MessagePopupBody message="Ви були успішно зареєстровані. Ввійдіть, щоб продовжити" />,
                footerGetter: setPopupState => <LinkFooter title="Увійти" to={page.login} setPopupState={setPopupState} />
            });
            setStatus(loadingStatus.completed);
        } else {
            setStatus(loadingStatus.inProgress);
        }
    }

    const getFirstColumn = () => {
        return (
            <div className="optimal register-form">
                <Input placeholder="Ім'я" value={name} onChange={event => setName(event.target.value)} />
                <Input placeholder="Прізвище" value={surname} onChange={event => setSurname(event.target.value)} />
                <Input placeholder="Курс" value={course} onChange={event => setCourse(event.target.value)} />
                <Input placeholder="Навчальний заклад" value={school} onChange={event => setSchool(event.target.value)} />
                <Input type="email" className="browser-default" placeholder="E-mail" value={email} onChange={event => setEmail(event.target.value)} />
                <Input type="password" className="browser-default" placeholder="Пароль" value={password} onChange={event => setPassword(event.target.value)} />
                <Input type="password" className="browser-default" placeholder="Повторити пароль" value={confirmPassword} onChange={event => setConfirmPassword(event.target.value)} />
                <div className="button center">
                    <LoaderBoundary condition={status === loadingStatus.loading} size="small">
                        {
                            status === loadingStatus.inProgress ? (
                                <PopupConsumer>
                                    {
                                        ({setPopupState}) => (
                                            <Button onClick={() => handleSignUp(setPopupState)}>
                                                Зареєструватися
                                            </Button>
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
