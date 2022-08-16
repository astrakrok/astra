import {useEffect, useState} from "react";
import Select from "react-select";
import {getAll} from "../../../service/specialization.service";
import {create, update} from "../../../service/subject.service";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../Spacer/Spacer";
import ErrorsArea from "../../ErrorsArea/ErrorsArea";
import {defaultSubject} from "../../../data/default/subject";
import V from "max-validator";
import "./SubjectForm.css";
import {subjectSchema} from "../../../validation/schema/subject";
import DisplayBoundary from "../../DisplayBoundary/DisplayBoundary";
import {toSelectValue} from "../../../mapper/specialization.mapper";

const SubjectForm = ({
    subject = defaultSubject,
    onSuccess = () => {}
}) => {
    const [title, setTitle] = useState(subject.title);
    const [specializations, setSpecializations] = useState(null);
    const [selectedSpecializations, setSelectedSpecializations] = useState(subject.specializations.map(toSelectValue));
    const [formState, setFormState] = useState({loading: false, errors: {}});

    useEffect(() => {
        const fetchSpecializations = async () => {
            const result = await getAll();
            setSpecializations(result);
        }

        fetchSpecializations();
    }, []);

    const getSpecializationOptions = () => {
        return (specializations) ? specializations.map(item => ({
            value: item.id,
            label: item.title
        })) : [];
    }

    const save = async () => {
        const subjectState = {
            id: subject.id,
            title: title,
            specializationIds: selectedSpecializations.map(item => item.value)
        };
        setFormState({
            loading: true,
            errors: {}
        });
        const validationResult = V.validate(subjectState, subjectSchema);
        if (validationResult.hasError) {
            setFormState({
                loading: false,
                errors: validationResult.errors
            });
            return;
        }
        const data = subject.id ? await update(subjectState) : await create(subjectState);
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
                        value={selectedSpecializations}
                        noOptionsMessage={() => "Не залишилось спеціалізацій"} />
                    <ErrorsArea errors={formState.errors.specializationIds} />
                </LoaderBoundary>
            </div>
            <Spacer height={20}/>
            <div className="s-vflex">
                <LoaderBoundary condition={formState.loading} size="small" className="s-hflex-center">
                    <div className="s-hflex-center">
                        <Button isFilled={true} onClick={() => save()}>
                            {subject.id ? "Підтвердити" : "Створити"}
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

export default SubjectForm;
