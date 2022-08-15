import {useState} from "react";
import {changePasswordSchema} from "../../../validation/scheme/changePassword";
import {changeUserPassword} from "../../../service/auth.service";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import Spacer from "../../Spacer/Spacer";
import ErrorsArea from "../../ErrorsArea/ErrorsArea";
import V from "max-validator";
import "./ChangePasswordForm.css";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import MessagePopupBody from "../../popup-component/MessagePopupBody/MessagePopupBody";
import {message} from "../../../constant/message";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";

const ChangePasswordForm = () => {
    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmNewPassword, setConfirmNewPassword] = useState("");
    const [formState, setFormState] = useState({
        loading: false,
        errors: {}
    });

    const getErrors = (validationResult, data) => {
        if (validationResult.hasError) {
            if (validationResult.errors.newPassword) {
                return validationResult.errors;
            }
        }
        const passwordConfirmationError = data.newPassword === data.confirmNewPassword ? null : {
            confirmNewPassword: ["Паролі не співпадають"]
        };
        const errors = {
            ...validationResult.errors,
            ...passwordConfirmationError
        }
        return Object.keys(errors).length === 0 ? null : errors;
    }

    const changePassword = async setPopupState => {
        const data = {
            oldPassword,
            newPassword,
            confirmNewPassword
        };

        const validationResult = V.validate(data, changePasswordSchema);
        const errors = getErrors(validationResult, data);
        if (errors) {
            setFormState({
                loading: false,
                errors: errors
            });
            return;
        }
        setFormState({
            loading: true,
            errors: {}
        });
        const result = await changeUserPassword(data);
        const popupMessage = result.error || message.changePasswordSucceed
        setPopupState({
            bodyGetter: () => <MessagePopupBody message={popupMessage}/>
        });
        setFormState({
            loading: false,
            errors: {}
        });
    }

    return (
        <div className="s-vflex-center ChangePasswordForm">
            <Input
                type="password"
                className="browser-default"
                value={oldPassword}
                onChange={event => setOldPassword(event.target.value)}
                placeholder="Старий пароль"/>
            <ErrorsArea errors={formState.errors.oldPassword}/>
            <Spacer height={10}/>
            <Input
                type="password"
                className="browser-default"
                value={newPassword}
                onChange={event => setNewPassword(event.target.value)}
                placeholder="Новий пароль"/>
            <ErrorsArea errors={formState.errors.newPassword}/>
            <Spacer height={10}/>
            <Input
                type="password"
                className="browser-default"
                value={confirmNewPassword}
                onChange={event => setConfirmNewPassword(event.target.value)}
                placeholder="Повторіть пароль"/>
            <ErrorsArea errors={formState.errors.confirmNewPassword}/>
            <Spacer height={10}/>
            <LoaderBoundary condition={formState.loading} className="s-hflex-center" size="small">
                <PopupConsumer>
                    {
                        ({setPopupState}) => (
                            <div className="s-hflex-end">
                                <Button isFilled={true} onClick={() => changePassword(setPopupState)}>
                                    Змінити
                                </Button>
                            </div>
                        )
                    }
                </PopupConsumer>
            </LoaderBoundary>
        </div>
    );
}

export default ChangePasswordForm;
