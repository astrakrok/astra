import {useState} from "react";
import {getUser} from "../../../handler/user.handler";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import "./ChangeUserInfoForm.css";

const ChangeUserInfoForm = () => {
    const user = getUser();

    const [name, setName] = useState(user.name);
    const [surname, setSurname] = useState(user.surname);

    const changeUserInfo = () => {
        console.log({
            name,
            surname
        });
    }

    return (
        <div className="s-vflex">
            <Input value={user.email} placeholder="Email" disabled="disabled" />
            <Input value={name} onChange={event => setName(event.target.value)} placeholder="Ім'я" />
            <Input value={surname} onChange={event => setSurname(event.target.value)} placeholder="Прізвище" />
            <div className="s-hflex-end">
                <Button isFilled={true} onClick={changeUserInfo}>
                    Зберегти
                </Button>
            </div>
        </div>
    );
}

export default ChangeUserInfoForm;
