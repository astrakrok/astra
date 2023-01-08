import {useEffect, useState} from "react";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import {getAllByStepId} from "../../../service/specialization.service";
import {getAll} from "../../../service/step.service";
import Button from "../../Button/Button";
import SubjectForm from "../../form/SubjectForm/SubjectForm";
import Input from "../../Input/Input";
import SingleSelect from "../../SingleSelect/SingleSelect";
import Spacer from "../../Spacer/Spacer";
import "./SubjectFilter.css";

const SubjectFilter = ({
    onFilterSelected = () => {}
}) => {
    const [steps, setSteps] = useState(null);
    const [specializations, setSpecializations] = useState(null);
    const [selectedStep, setSelectedStep] = useState(null);
    const [selectedSpecialization, setSelectedSpecialization] = useState(null);
    const [searchText, setSearchText] = useState("");

    useEffect(() => {
        const fetchSteps = async () => {
            const result = await getAll();
            setSteps(result.map(item => ({
                value: item.id,
                label: item.title
            })));
        }

        fetchSteps();
    }, []);

    useEffect(() => {
        const fetchSpecializations = async () => {
            setSelectedSpecialization(null);
            setSpecializations(null);
            if (selectedStep) {
                const result = await getAllByStepId(selectedStep.value);
                setSpecializations(result.map(item => ({
                    value: item.id,
                    label: item.title
                })));
            } else {
                setSpecializations([]);
            }
        }

        fetchSpecializations();
    }, [selectedStep]);

    const applyFilter = () => {
        const filter = {
            searchText: searchText,
            stepId: selectedStep ? selectedStep.value : null,
            specializationId: selectedSpecialization ? selectedSpecialization.value : null
        }

        onFilterSelected(filter);
    }

    const onSubjectSaved = (setPopupState) => {
        setPopupState();
        applyFilter();
    }

    const subjectForm = (setPopupState, subject) => {
        return (
            <SubjectForm onSuccess={() => onSubjectSaved(setPopupState)} subject={subject} />
        );
    }

    const openPopup = (setPopupState, subject) => {
        setPopupState({
            bodyGetter: () => subjectForm(setPopupState, subject)
        });
    }

    return (
        <div className="SubjectFilter s-vflex">
            <div className="s-hflex-end">
                <Input
                    placeholder="Пошук по предмету"
                    value={searchText}
                    withLabel={false}
                    className="full-height"
                    onChange={event => setSearchText(event.target.value)} />
                <Spacer width={15} />
                <SingleSelect
                    className="equal-flex"
                    value={selectedStep}
                    isClearable={true}
                    placeholder="Виберіть КРОК"
                    options={steps}
                    isLoading={steps == null}
                    onChange={setSelectedStep} />
                <Spacer width={15} />
                <SingleSelect
                    className="equal-flex"
                    value={selectedSpecialization}
                    isClearable={true}
                    placeholder="Виберіть спеціалізацію"
                    options={specializations}
                    isLoading={specializations == null}
                    onChange={setSelectedSpecialization} />
            </div>
            <Spacer height={20} />
            <div className="s-hflex-end">
                <PopupConsumer>
                    {
                        ({setPopupState}) => (
                            <Button onClick={() => openPopup(setPopupState)}>
                                Створити предмет
                            </Button>
                        )
                    }
                </PopupConsumer>
                <Spacer width={20} />
                <Button isFilled={true} onClick={applyFilter}>Застосувати</Button>
            </div>
        </div>
    );
}

export default SubjectFilter;
