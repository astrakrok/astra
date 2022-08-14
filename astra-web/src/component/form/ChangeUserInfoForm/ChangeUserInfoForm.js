import {useState} from "react";
import {defaultUser} from "../../../data/default/user";
import {update} from "../../../service/user.service";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../Spacer/Spacer";
import V from "max-validator";
import "./ChangeUserInfoForm.css";
import {updateUserSchema} from "../../../validation/scheme/updateUser";
import ErrorsArea from "../../ErrorsArea/ErrorsArea";
import DisplayBoundary from "../../DisplayBoundary/DisplayBoundary";

const ChangeUserInfoForm = ({
                                user = defaultUser,
                                onSuccess = () => {
                                }
                            }) => {
    const [name, setName] = useState(user.name);
    const [surname, setSurname] = useState(user.surname);
    const [course, setCourse] = useState(user.course);
    const [school, setSchool] = useState(user.school);
    const [formState, setFormState] = useState({
        loading: false,
        errors: {}
    });

    const changeUserInfo = async () => {
        setFormState({
            loading: true,
            errors: {}
        });
        const newUserData = {
            name,
            surname,
            course,
            school
        };
        const validationResult = V.validate(newUserData, updateUserSchema);
        if (validationResult.hasError) {
            setFormState({
                loading: false,
                errors: validationResult.errors
            });
            return;
        }
        const result = await update(newUserData);
        if (!result.error) {
            onSuccess({
                ...user,
                ...newUserData
            });
            return;
        }
        setFormState({
            loading: false,
            errors: {
                global: [result.error]
            }
        });
    }

    return (
        <div className="s-vflex ChangeUserInfoForm">
            <Input value={user.email} placeholder="Email" disabled="disabled"/>
            <Spacer height={10}/>
            <Input value={name} onChange={event => setName(event.target.value)} placeholder="Ім'я"/>
            <ErrorsArea errors={formState.errors.name}/>
            <Spacer height={10}/>
            <Input value={surname} onChange={event => setSurname(event.target.value)} placeholder="Прізвище"/>
            <ErrorsArea errors={formState.errors.surname}/>
            <Spacer height={10}/>
            <Input value={course} onChange={event => setCourse(event.target.value)} placeholder="Курс"/>
            <Spacer height={10}/>
            <Input value={school} onChange={event => setSchool(event.target.value)} placeholder="Навчальний заклад"/>
            <Spacer height={10}/>
            <LoaderBoundary condition={formState.loading} size="small" className="s-hflex-center">
                <DisplayBoundary condition={formState.errors.global}>
                    <Spacer height={20}/>
                    <ErrorsArea errors={formState.errors.global}/>
                    <Spacer height={10}/>
                </DisplayBoundary>
                <div className="s-hflex-end">
                    <Button isFilled={true} onClick={changeUserInfo}>
                        Зберегти
                    </Button>
                </div>
            </LoaderBoundary>
        </div>
    );
}

export default ChangeUserInfoForm;
