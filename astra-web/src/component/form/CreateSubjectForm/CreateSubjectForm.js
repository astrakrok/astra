import {useEffect, useState} from "react";
import Select from "react-select";
import {getAll} from "../../../service/specialization.service";
import {create} from "../../../service/subject.service";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../Spacer/Spacer";
import ErrorsArea from "../../ErrorsArea/ErrorsArea";
import V from "max-validator";
import "./CreateSubjectForm.css";
import {subjectSchema} from "../../../validation/scheme/subject";
import DisplayBoundary from "../../DisplayBoundary/DisplayBoundary";

const CreateSubjectForm = ({
    onSuccess = () => {}
}) => {
    const [title, setTitle] = useState("");
    const [specializations, setSpecializations] = useState(null);
    const [selectedSpecializations, setSelectedSpecializations] = useState([]);
    const [formState, setFormState] = useState({loading: false, errors: {}});
    
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
        setFormState({
            loading: true,
            errors: {}
        });
        const validationResult = V.validate(subject, subjectSchema);
        if (validationResult.hasError) {
            setFormState({
                loading: false,
                errors: validationResult.errors
            });
            return;
        }
        const data = await create(subject);
        if (data.error) {
            setFormState({
                loading: false,
                errors: {
                    global: [data.error]
                }
            });
            return;
        }
        onSuccess(data);
    }

    return (
        <div className="s-vflex-center modal-content">
            <Input
                withLabel={false}
                placeholder="Назва предмету"
                value={title}
                onChange={event => setTitle(event.target.value)}/>
            <ErrorsArea errors={formState.errors.title} />
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
                    <ErrorsArea errors={formState.errors.specializationIds} />
                </LoaderBoundary>
            </div>
            <Spacer height={20}/>
            <div className="s-vflex">
                <LoaderBoundary condition={formState.loading} size="small">
                    <div className="s-hflex-center">
                        <Button isFilled={true} onClick={() => createSubject()}>
                            Підтвердити
                        </Button>
                    </div>
                    <DisplayBoundary condition={formState.errors.global}>
                        <Spacer height={15} />
                        <div className="center">
                            <ErrorsArea errors={formState.errors.global} />
                        </div>
                    </DisplayBoundary>
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default CreateSubjectForm;
