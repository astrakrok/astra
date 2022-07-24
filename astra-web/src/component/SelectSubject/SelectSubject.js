import {useState} from "react";
import SingleSelect from "../SingleSelect/SingleSelect";
import "./SelectSubject.css";
import Spacer from "../Spacer/Spacer";
import Button from "../Button/Button";

const SelectSubject = ({
    subjectsDetails = [],
    onSave = () => {}
}) => {
    const [selectedSubject, setSelectedSubject] = useState(null);

    const getSpecializationsString = specializations => {
        return specializations.map(item => item.title).join(", ");
    }

    const getSubjectsOptions = () => {
        return subjectsDetails.map(item => ({
            value: item.id,
            label: item.title + "(" + getSpecializationsString(item.specializations) + ")"
        }));
    }

    const save = () => {
        onSave({
            id: selectedSubject.value,
            title: selectedSubject.label
        });
    }

    return (
        <div className="s-vflex full-width">
            <SingleSelect
                placeholder="Виберіть предмет"
                onChange={setSelectedSubject}
                options={getSubjectsOptions()} />
            <Spacer height={20}/>
            <div className="s-hflex-center">
                <Button isFilled={true} onClick={save} disabled={selectedSubject == null}>
                    Зберегти
                </Button>
            </div>
        </div>
    );
}

export default SelectSubject;
