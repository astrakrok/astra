import {useState} from "react";
import {defaultUser} from "../../../data/default/user";
import {update} from "../../../service/user.service";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import "./ChangeUserInfoForm.css";

const ChangeUserInfoForm = ({
    user = defaultUser,
    onSuccess = () => {}
}) => {
    const [name, setName] = useState(user.name);
    const [surname, setSurname] = useState(user.surname);
    const [course, setCourse] = useState(user.course);
    const [school, setSchool] = useState(user.school);

    const changeUserInfo = async () => {
        const newUserData = {
            name,
            surname,
            course,
            school
        };
        await update(newUserData);
        onSuccess(newUserData);
    }

    return (
        <div className="s-vflex">
            <Input value={user.email} placeholder="Email" disabled="disabled" />
            <Input value={name} onChange={event => setName(event.target.value)} placeholder="Ім'я" />
            <Input value={surname} onChange={event => setSurname(event.target.value)} placeholder="Прізвище" />
            <Input value={course} onChange={event => setCourse(event.target.value)} placeholder="Курс" />
            <Input value={school} onChange={event => setSchool(event.target.value)} placeholder="Навчальний заклад" />
            <div className="s-hflex-end">
                <Button isFilled={true} onClick={changeUserInfo}>
                    Зберегти
                </Button>
            </div>
        </div>
    );
}

export default ChangeUserInfoForm;
