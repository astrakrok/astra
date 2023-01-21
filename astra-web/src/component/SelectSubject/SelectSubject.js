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

    const getSubjectsOptions = () => {
        return subjectsDetails.map(item => ({
            value: item.id,
            label: item.title + "(" + item.specialization.step.title + " | " + item.specialization.title + ")"
        }));
    }

    const save = () => {
        const subject = subjectsDetails.find(item => item.id*1 === selectedSubject.value*1);
        onSave(subject);
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
