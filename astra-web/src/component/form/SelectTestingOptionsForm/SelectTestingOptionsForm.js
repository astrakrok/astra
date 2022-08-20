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

const SelectTestingOptionsForm = ({
                                      exams,
                                      onExamSelected = () => [],
                                      onSelected = () => null,
                                      descriptions,
                                      onSelect = () => {
                                      }
                                  }) => {
    const [specializations, setSpecializations] = useState(null);
    const [selectedExam, setSelectedExam] = useState(null);
    const [selectedSpecialization, setSelectedSpecialization] = useState(null);
    const [mode, setMode] = useState();
    const [count, setCount] = useState();

    useEffect(() => {
        setSelectedSpecialization(null);
        if (!selectedExam) {
            return;
        }
        setSpecializations(onExamSelected(selectedExam.value));
    }, [selectedExam]);

    const selected = event => {
        event.preventDefault();
        const options = {
            testingId: onSelected(selectedExam.value, selectedSpecialization.value),
            mode: mode.value
        };
        if (options.mode === "training") {
            options.count = count.value;
        }
        onSelect(options);
    }

    const isValidOptions = () => {
        const isValidMainData = [selectedSpecialization, mode].filter(item => item == null).length === 0;
        return isValidMainData && (mode.value === "examination" || (mode.value === "training" && count != null));
    }

    const getSpecializationsOptions = () => {
        return specializations == null ? [] : specializations.map(item => ({
            label: item.title,
            value: item.id
        }));
    }

    const getExamsOptions = () => {
        return exams == null ? [] : exams.map(item => ({
            label: item.title,
            value: item.id
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
                            placeholder="Виберіть рік"
                            options={getExamsOptions()}
                            onChange={updateExam}/>
                        <Spacer height={20}/>
                        <SingleSelect
                            placeholder="Виберіть спеціалізацію"
                            options={getSpecializationsOptions()}
                            onChange={setSelectedSpecialization}
                            value={selectedSpecialization}/>
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
                            {
                                mode.value === "training" ? descriptions.trainingDescription : descriptions.examinationDescription
                            }
                        </InfoBlock>
                    )
                }
            </div>
        </div>
    );
}

export default SelectTestingOptionsForm;
