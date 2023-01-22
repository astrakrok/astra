import {useState} from "react";
import SingleSelect from "../SingleSelect/SingleSelect";
import "./SelectSubject.css";
import Spacer from "../Spacer/Spacer";
import Button from "../Button/Button";
import {throttle} from "../../handler/common.handler";
import {getSubjectsDetailsPage} from "../../service/subject.service";

const load = throttle(async (value, setSubjects) => {
    if (!value || value.length < 1) {
        return;
    }
    const result = await getSubjectsDetailsPage({searchText: value}, {pageSize: 5, pageNumber: 0});
    setSubjects(result.items);
}, 2000);

const SelectSubject = ({
    onSave = () => {}
}) => {
    const [subjects, setSubjects] = useState([]);
    const [selectedSubject, setSelectedSubject] = useState(null);

    const loadOptions = value => {
        load(value, setSubjects);
    }

    const getSubjectsOptions = () => subjects.map(item => ({
        value: item.id,
        label: item.title + "(" + item.specialization.step.title + " | " + item.specialization.title + ")"
    }));

    const save = () => {
        const subject = subjects.find(item => item.id*1 === selectedSubject.value*1);
        onSave(subject);
    }

    return (
        <div className="s-vflex full-width">
            <SingleSelect
                placeholder="Почніть вводити текст для пошуку предметів"
                onInputChange={loadOptions}
                value={selectedSubject}
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
