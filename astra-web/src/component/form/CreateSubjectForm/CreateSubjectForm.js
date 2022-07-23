import {useEffect, useState} from "react";
import Select from "react-select";
import {getAll} from "../../../service/specialization.service";
import {create} from "../../../service/subject.service";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../Spacer/Spacer";
import "./CreateSubjectForm.css";

const CreateSubjectForm = ({
    onSuccess = () => {}
}) => {
    const [title, setTitle] = useState("");
    const [specializations, setSpecializations] = useState(null);
    const [selectedSpecializations, setSelectedSpecializations] = useState([]);
    const [loading, setLoading] = useState(false);
    
    useEffect(() => {
        const fetchSpecializations = async () => {
            const result = await getAll();
            setSpecializations(result);
        }

        fetchSpecializations();
    })

    const getSpecializationOptions = () => {
        return (specializations) ? specializations.map(item => ({
            value: item.id,
            label: item.title
        })) : [];
    }

    const createSubject = async () => {
        const subject = {
            title: title,
            specializationIds: selectedSpecializations.map(item => item.value)
        };
        setLoading(true);
        const data = await create(subject);
        onSuccess(data);
    }

    return (
        <div className="s-vflex-center modal-content">
            <Input
                withLabel={false}
                placeholder="Назва предмету"
                value={title}
                onChange={event => setTitle(event.target.value)}/>
            <Spacer height={10}/>
            <div className="center">
                <LoaderBoundary condition={specializations == null} size="small">
                    <Select
                        options={getSpecializationOptions()}
                        isMulti={true}
                        onChange={setSelectedSpecializations}
                        className="specializations"
                        placeholder="Спеціалізації"
                        noOptionsMessage={() => "Не залишилось спеціалізацій"} />
                </LoaderBoundary>
            </div>
            <Spacer height={20}/>
            <div className="s-hflex-center">
                <LoaderBoundary condition={loading} size="small">
                    <Button isFilled={true} onClick={() => createSubject()}>
                        Підтвердити
                    </Button>
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default CreateSubjectForm;
