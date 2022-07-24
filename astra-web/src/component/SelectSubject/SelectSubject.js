import {useEffect, useState} from "react";
import SingleSelect from "../SingleSelect/SingleSelect";
import {getSubjectsBySpecializationId} from "../../service/subject.service";
import "./SelectSubject.css";
import Spacer from "../Spacer/Spacer";
import Button from "../Button/Button";

const SelectSubject = ({
    specializations = [],
    onSave = () => {}
}) => {
    const [selectedSpecialization, setSelectedSpecialization] = useState({});
    const [subjects, setSubjects] = useState(null);
    const [selectedSubject, setSelectedSubject] = useState(null);
    const specializationsOptions = specializations.map(item => ({value: item.id, label: item.title}));

    useEffect(() => {
        setSelectedSubject(null);
        if (!selectedSpecialization.value) {
            setSubjects(null);
            return;
        }
        const fetchSubjects = async () => {
            const result = await getSubjectsBySpecializationId(selectedSpecialization.value);
            setSubjects(result);
        }

        fetchSubjects();
    }, [selectedSpecialization]);

    const setIfChanged = newSelectedSpecialization => {
        if (newSelectedSpecialization.value !== selectedSpecialization.value) {
            setSelectedSpecialization(newSelectedSpecialization);
        }
    }

    const getSubjectsOptions = () => {
        return subjects.map(item => ({value: item.id, label: item.title}));
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
                placeholder="Виберіть спеціалізацію"
                onChange={setIfChanged}
                options={specializationsOptions} />
            {
                subjects == null ? null : (
                    <>
                        <Spacer height={20} />
                        <SingleSelect
                            placeholder="Виберіть предмет"
                            value={selectedSubject}
                            onChange={setSelectedSubject}
                            options={getSubjectsOptions()} />
                    </>
                )
            }
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
