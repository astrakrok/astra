import {useState} from "react";
import CardTemplate from "../../CardTemplate/CardTemplate.js";
import Input from "../../Input/Input";
import Button from "../../Button/Button";
import Spacer from "../../Spacer/Spacer";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import PopupConsumer from "../../../context/popup/PopupConsumer"
import MessagePopupBody from "../../popup-component/MessagePopupBody/MessagePopupBody";
import "./ResetPasswordPage.css";
import {resetPassword} from "../../../service/auth.service.js";
import {message} from "../../../constant/message.js";
import V from "max-validator";
import {emailSchema} from "../../../validation/schema/email.js";
import ErrorsArea from "../../ErrorsArea/ErrorsArea";

const ResetPasswordPage = () => {
    const [email, setEmail] = useState("");
    const [formState, setFormState] = useState({
        loading: false,
        errors: {}
    });

    const resetPasswordClickHandler = async setPopupState => {
        setFormState({
            loading: true,
            errors: {}
        });
        const validationResult = V.validate({email}, emailSchema);
        if (validationResult.hasError) {
            setFormState({
                loading: false,
                errors: validationResult.errors
            });
            return;
        }

        const result = await resetPassword(email);
        setFormState({
            loading: false,
            errors: {}
        });
        const resultMessage = result.error || message.resetPasswordEmail;
        setPopupState({
            bodyGetter: () => <MessagePopupBody message={resultMessage}/>
        });
    }

    const renderFormContent = () => (
        <div className="s-vflex form-content">
            <Input
                value={email}
                onChange={event => setEmail(event.target.value)}
                placeholder="Ваш E-mail"/>
            <ErrorsArea errors={formState.errors.email}/>
            <Spacer height={20}/>
            <div className="s-hflex-center">
                <LoaderBoundary condition={formState.loading} size="small">
                    <PopupConsumer>
                        {
                            ({setPopupState}) => (
                                <Button isFilled={true} onClick={() => resetPasswordClickHandler(setPopupState)}>
                                    Відновити
                                </Button>
                            )
                        }
                    </PopupConsumer>
                </LoaderBoundary>
            </div>
        </div>
    );

    return (
        <div className="container ResetPasswordPage">
            <div className="row">
                <div className="s-hflex-center email-form-wrapper">
                    <CardTemplate
                        heading={<h5>Відновити пароль</h5>}
                        content={renderFormContent()}/>
                </div>
            </div>
        </div>
    );
}

export default ResetPasswordPage
