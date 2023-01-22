import {useEffect, useState} from "react";
import {filter} from "../../../service/specialization.service";
import {getAll as getAllSteps} from "../../../service/step.service";
import {getAll as getAllExams} from "../../../service/exam.service";
import SingleSelect from "../../SingleSelect/SingleSelect";
import Spacer from "../../Spacer/Spacer";
import Alert from "../../Alert/Alert";
import "./SelectTestingForm.css";
import Button from "../../Button/Button";

const SelectTestingForm = ({
    testId,
    onTestingSelected = () => {}
}) => {
    const [steps, setSteps] = useState(null);
    const [specializations, setSpecializations] = useState([]);
    const [exams, setExams] = useState(null);
    const [selectedStep, setSelectedStep] = useState(null);
    const [selectedSpecialization, setSelectedSpecialization] = useState(null);
    const [selectedExam, setSelectedExam] = useState(null);

    useEffect(() => {
        const fetchSteps = async () => {
            const steps = await getAllSteps();
            setSteps(steps.map(item => ({
                value: item.id,
                label: item.title
            })));
        }

        const fetchExams = async () => {
            const exams = await getAllExams();
            setExams(exams.map(item => ({
                value: item.id,
                label: item.title
            })));
        }

        fetchSteps();
        fetchExams();
    }, []);

    useEffect(() => {
        const fetchSpecializations = async () => {
            setSelectedSpecialization(null);
            setSpecializations(null);
            if (selectedStep) {
                const specializations = await filter({stepId: selectedStep.value, testId: testId});
                setSpecializations(specializations.map(item => ({
                    value: item.id,
                    label: item.title
                })));
            } else {
                setSpecializations([]);
            }
        }

        fetchSpecializations();
    }, [selectedStep]);

    const confirmSelection = () => {
        onTestingSelected({
            exam: {
                id: selectedExam.value,
                title: selectedExam.label
            },
            specialization: {
                id: selectedSpecialization.value,
                title: selectedSpecialization.label
            }
        });
    }

    return (
        <div className="SelectTestingForm s-vflex">
            <Alert type="warning">
                <span className="weight-strong">УВАГА! </span> Переконайтесь, що Ви зберегли основну інформацію про тест, для того, щоб Astra могла підібрати корректний список можливих спеціалізації для даного тесту!
            </Alert>
            <Spacer height={20} />
            <SingleSelect
                isClearable={true}
                noOptionsMessage={() => "Немає доступних КРОКів"}
                isLoading={steps == null}
                placeholder="Виберіть КРОК"
                value={selectedStep}
                onChange={setSelectedStep}
                options={steps}/>
            <Spacer height={15} />
            <SingleSelect
                isClearable={true}
                noOptionsMessage={() => "Немає доступних спеціалізацій"}
                isLoading={specializations == null}
                placeholder="Виберіть спеціалізацію"
                value={selectedSpecialization}
                onChange={setSelectedSpecialization}
                options={specializations}/>
                <Spacer height={15} />
            <SingleSelect
                isClearable={true}
                noOptionsMessage={() => "Немає доступних іспитів"}
                isLoading={exams == null}
                placeholder="Виберіть іспит"
                value={selectedExam}
                onChange={setSelectedExam}
                options={exams}/>
            <Spacer height={20} />
            <div className="s-hflex-end">
                <Button isFilled={true} disabled={selectedExam == null || selectedSpecialization == null} onClick={confirmSelection}>
                    Підтвердити
                </Button>
            </div>
        </div>
    );
}

export default SelectTestingForm;
