import {useState} from "react";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
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
        <div className="s-vflex-center">
            <Input
                type="password"
                className="browser-default"
                value={oldPassword}
                onChange={event => setOldPassword(event.target.value)}
                placeholder="Старий пароль" />
            <Input
                type="password"
                className="browser-default"
                value={newPassword}
                onChange={event => setNewPassword(event.target.value)}
                placeholder="Новий пароль" />
            <Input
                type="password"
                className="browser-default"
                value={confirmNewPassword}
                onChange={event => setConfirmNewPassword(event.target.value)}
                placeholder="Повторіть пароль" />
            <div className="s-hflex-end">
                <Button isFilled={true} onClick={changePassword}>
                    Змінити
                </Button>
            </div>
        </div>
    );
}

export default ChangePasswordForm;
