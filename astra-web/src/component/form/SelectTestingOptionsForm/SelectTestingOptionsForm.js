import {useEffect, useState} from "react";
import {testingModes} from "../../../constant/testing.mode";
import {questionsModes} from "../../../constant/questions.mode";
import SingleSelect from "../../SingleSelect/SingleSelect";
import Button from "../../Button/Button";
import Spacer from "../../Spacer/Spacer";
import DisplayBoundary from "../../DisplayBoundary/DisplayBoundary";
import "./SelectTestingOptionsForm.css";
import InfoHeader from "../../InfoHeader/InfoHeader";
import InfoBlock from "../../InfoBlock/InfoBlock";
import parse from "html-react-parser";

const SelectTestingOptionsForm = ({
    stepsList,
    onStepSelected = () => {},
    specializationsList,
    onSpecializationSelected = () => {},
    examsList,
    onSelected = () => null,
    descriptions,
    onSelect = () => {}
}) => {
    const [steps, setSteps] = useState(stepsList);
    const [selectedStep, setSelectedStep] = useState(null);
    const [specializations, setSpecializations] = useState(specializationsList);
    const [selectedSpecialization, setSelectedSpecialization] = useState(null);
    const [exams, setExams] = useState(examsList);
    const [selectedExam, setSelectedExam] = useState(null);
    const [mode, setMode] = useState();
    const [count, setCount] = useState();

    useEffect(() => {
        if (selectedStep) {
            onStepSelected(selectedStep.value);
        }
    }, [selectedStep]);

    useEffect(() => {
        if (selectedSpecialization) {
            onSpecializationSelected(selectedSpecialization.value);
        }
    }, [selectedSpecialization]);

    useEffect(() => {
        setSteps(stepsList);
        setSelectedStep(null);
        setSelectedSpecialization(null);
        setSelectedExam(null);
    }, [stepsList]);

    useEffect(() => {
        setSelectedSpecialization(null);
        setSelectedExam(null);
        setSpecializations(specializationsList);
    }, [specializationsList]);

    useEffect(() => {
        setExams(examsList);
        setSelectedExam(null);
    }, [examsList]);

    const selected = async event => {
        event.preventDefault();
        const testing = (mode && mode.value === "adaptive") ? {} : await onSelected(selectedExam.value, selectedSpecialization.value);
        const options = {
            testingId: testing ? testing.id : null,
            examId: selectedExam && selectedExam.value,
            specializationId: selectedSpecialization && selectedSpecialization.value,
            mode: mode.value
        };
        if (options.mode === "training") {
            options.count = count.value;
        }
        onSelect(options);
    }

    const getStepsOptions = () => {
        return steps == null ? [] : steps.map(step => ({
            value: step.id,
            label: step.title
        }));
    }

    const updateStep = newStep => {
        if (!newStep && !selectedStep) {
            return;
        } else if (newStep && selectedStep) {
            if (newStep.value !== selectedStep.value) {
                setSelectedStep(newStep);
            }
            return;
        }
        setSelectedStep(newStep);
    }

    const getSpecializationsOptions = () => {
        return specializations == null ? [] : specializations.map(specialization => ({
            value: specialization.id,
            label: specialization.title
        }));
    }

    const updateSpecialization = newSpecialization => {
        if (!newSpecialization && !selectedSpecialization) {
            return;
        } else if (newSpecialization && selectedSpecialization) {
            if (newSpecialization.value !== selectedSpecialization.value) {
                setSelectedSpecialization(newSpecialization);
            }
            return;
        }
        setSelectedSpecialization(newSpecialization);
    }

    const getExamsOptions = () => {
        return exams == null ? [] : exams.map(exam => ({
            value: exam.id,
            label: exam.title
        }));
    }

    const updateExam = newExam => {
        if (!newExam && !selectedExam) {
            return;
        } else if (newExam && selectedExam) {
            if (newExam.value !== selectedExam.value) {
                setSelectedExam(newExam);
            }
            return;
        }
        setSelectedExam(newExam);
    }

    const isValidOptions = () => {
        if (!mode) {
            return false;
        }
        if (mode.value === "examination") {
            if (!selectedExam) {
                return false;
            }
        } else if (mode.value === "training") {
            if (!selectedExam || count == null) {
                return false;
            }
        } else if (mode.value === "adaptive") {
            if (!selectedSpecialization) {
                return false;
            }
        }
        return true;
    }

    return (
        <div className="s-vflex">
            <div className="s-hflex-center">
                <div className="SelectTestingOptionsForm s-vflex-center full-width">
                    <form method="post" onSubmit={selected}>
                        <div className="mode s-vflex">
                            <InfoHeader>
                                Режим
                            </InfoHeader>
                            <SingleSelect
                                placeholder="Виберіть режим"
                                options={testingModes}
                                onChange={setMode}/>
                            <Spacer height={20}/>
                            <DisplayBoundary condition={mode && mode.value === "training"}>
                                <SingleSelect
                                    placeholder="Виберіть кількість питань"
                                    options={questionsModes}
                                    onChange={setCount}/>
                            </DisplayBoundary>
                            <Spacer height={20}/>
                        </div>
                        <div className="testing s-vflex">
                            <InfoHeader>
                                Тестування
                            </InfoHeader>
                        </div>
                        <SingleSelect
                            value={selectedStep}
                            placeholder="Виберіть КРОК"
                            options={getStepsOptions()}
                            onChange={updateStep}/>
                        <DisplayBoundary condition={selectedStep != null}>
                            <>
                                <Spacer height={20}/>
                                <SingleSelect
                                    value={selectedSpecialization}
                                    placeholder="Виберіть Спеціалізацю"
                                    options={getSpecializationsOptions()}
                                    onChange={updateSpecialization}/>
                            </>
                        </DisplayBoundary>
                        <DisplayBoundary condition={selectedSpecialization != null && mode.value !== 'adaptive'}>
                            <>
                                <Spacer height={20}/>
                                <SingleSelect
                                    value={selectedExam}
                                    placeholder="Виберіть рік"
                                    options={getExamsOptions()}
                                    onChange={updateExam}/>
                            </>
                        </DisplayBoundary>
                        <Spacer height={40}/>
                        <div className="s-hflex-center">
                            <Button isFilled={true} disabled={!isValidOptions()} isSubmit="submit">
                                Розпочати
                            </Button>
                        </div>
                    </form>
                </div>
            </div>
            <Spacer height={40}/>
            <div className="testing-info">
                {
                    mode == null ? null : (
                        <InfoBlock>
                            <DisplayBoundary condition={mode.value === "training"}>
                                {parse(descriptions.trainingDescription)}
                            </DisplayBoundary>
                            <DisplayBoundary condition={mode.value === "examination"}>
                                {parse(descriptions.examinationDescription)}
                            </DisplayBoundary>
                            <DisplayBoundary condition={mode.value === "adaptive"}>
                                {parse(descriptions.adaptiveDescription)}
                            </DisplayBoundary>
                        </InfoBlock>
                    )
                }
            </div>
        </div>
    );
}

export default SelectTestingOptionsForm;
