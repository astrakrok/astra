import {useState} from "react";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import Spacer from "../../Spacer/Spacer";
import "./ChangePasswordForm.css";

const ChangePasswordForm = () => {
    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmNewPassword, setConfirmNewPassword] = useState("");

    const changePassword = () => {
        console.log({
            oldPassword,
            newPassword,
            confirmNewPassword
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
            <Spacer height={10}/>
            <Input
                type="password"
                className="browser-default"
                value={newPassword}
                onChange={event => setNewPassword(event.target.value)}
                placeholder="Новий пароль"/>
            <Spacer height={10}/>
            <Input
                type="password"
                className="browser-default"
                value={confirmNewPassword}
                onChange={event => setConfirmNewPassword(event.target.value)}
                placeholder="Повторіть пароль"/>
            <Spacer height={10}/>
            <div className="s-hflex-end">
                <Button isFilled={true} onClick={changePassword}>
                    Змінити
                </Button>
            </div>
        </div>
    );
}

export default ChangePasswordForm;
