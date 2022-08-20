import {useState} from "react";
import {defaultUser} from "../../../data/default/user";
import {update} from "../../../service/user.service";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../Spacer/Spacer";
import V from "max-validator";
import "./ChangeUserInfoForm.css";
import {updateUserSchema} from "../../../validation/schema/updateUser";
import ErrorsArea from "../../ErrorsArea/ErrorsArea";
import DisplayBoundary from "../../DisplayBoundary/DisplayBoundary";
import SingleSelect from "../../SingleSelect/SingleSelect";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import MessagePopupBody from "../../popup-component/MessagePopupBody/MessagePopupBody";
import {message} from "../../../constant/message";

const findSpecializationById = (specializations, specializationId) => {
    const specialization = specializations.find(item => item.id === specializationId);
    if (specialization) {
        return {
            value: specialization.id,
            label: specialization.title
        };
    }
    return null;
};

const ChangeUserInfoForm = ({
                                specializations,
                                user = defaultUser,
                                onSuccess = () => {
                                },
                            }) => {
    const [name, setName] = useState(user.name);
    const [surname, setSurname] = useState(user.surname);
    const [course, setCourse] = useState(user.course);
    const [school, setSchool] = useState(user.school);
    const [selectedSpecialization, setSelectedSpecialization] = useState(findSpecializationById(specializations, user.specializationId));
    const [formState, setFormState] = useState({
        loading: false,
        errors: {}
    });

    const specializationsOptions = () => specializations.map(item => ({label: item.title, value: item.id}));

    const changeUserInfo = async setPopupState => {
        setFormState({
            loading: true,
            errors: {}
        });
        const newUserData = {
            name,
            surname,
            course,
            school,
            specializationId: selectedSpecialization ? selectedSpecialization.value : null
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
            setFormState({
                loading: false,
                errors: {}
            });
            setPopupState({
                bodyGetter: () => <MessagePopupBody message={message.changeUserInfoSucceed}/>
            });
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
            <ErrorsArea errors={formState.errors.course}/>
            <Spacer height={10}/>
            <Input value={school} onChange={event => setSchool(event.target.value)} placeholder="Навчальний заклад"/>
            <ErrorsArea errors={formState.errors.school}/>
            <Spacer height={10}/>
            <div className="specialization-select s-vflex">
                <label>Спеціалізація</label>
                <SingleSelect
                    placeholder="Оберіть спеціалізацію"
                    options={specializationsOptions()}
                    onChange={setSelectedSpecialization}
                    value={selectedSpecialization}/>
            </div>
            <Spacer height={20}/>
            <LoaderBoundary condition={formState.loading} size="small" className="s-hflex-center">
                <DisplayBoundary condition={formState.errors.global}>
                    <Spacer height={20}/>
                    <ErrorsArea errors={formState.errors.global}/>
                    <Spacer height={10}/>
                </DisplayBoundary>
                <div className="s-hflex-end">
                    <PopupConsumer>
                        {
                            ({setPopupState}) => (
                                <Button isFilled={true} onClick={() => changeUserInfo(setPopupState)}>
                                    Зберегти
                                </Button>
                            )
                        }
                    </PopupConsumer>
                </div>
            </LoaderBoundary>
        </div>
    );
}

export default ChangeUserInfoForm;
