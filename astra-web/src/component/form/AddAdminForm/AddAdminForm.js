import {useState} from "react";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import Spacer from "../../Spacer/Spacer";
import "./AddAdminForm.css";

const AddAdminForm = () => {
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    return (
        <div className="AddAdminForm s-vflex">
            <Input placeholder="Прізвище" value={surname} onChange={event => setSurname(event.target.value)}/>
            <Spacer height={20}/>
            <Input placeholder="Ім'я" value={name} onChange={event => setName(event.target.value)}/>
            <Spacer height={20}/>
            <Input placeholder="E-mail" value={email} onChange={event => setEmail(event.target.value)}/>
            <Spacer height={20}/>
            <Input placeholder="Пароль" value={password} onChange={event => setPassword(event.target.value)}/>
            <Spacer height={20}/>
            <Input placeholder="Повторіть пароль" value={confirmPassword}
                   onChange={event => setConfirmPassword(event.target.value)}/>
            <Spacer height={20}/>
            <div className="s-hflex-center">
                <Button isFilled={true}>
                    Додати
                </Button>
            </div>
        </div>
    );
}

export default AddAdminForm;
