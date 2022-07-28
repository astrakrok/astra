import {useState} from "react";
import {testingModes} from "../../../constant/testing.mode";
import {questionsModes} from "../../../constant/questions.mode";
import SingleSelect from "../../SingleSelect/SingleSelect";
import Button from "../../Button/Button";
import Spacer from "../../Spacer/Spacer";
import "./SelectTestingOptionsForm.css";

const examToSelectValue = exam => {
    return exam ? {
        label: exam.title,
        value: exam.id
    } : null;
}

const specializationToSelectValue = specialization => {
    return specialization ? {
        label: specialization.title,
        value: specialization.id
    } : null;
}

const findExamById = (exams, examId) => exams.find(item => item.id === examId);

const findSpecializationById = (specializations, specializationId) => specializations.find(item => item.id === specializationId);

const findTestingModyByValue = value => testingModes.find(item => item.value === value);

const findQuestionModeByValue = value => questionsModes.find(item => item.value === value);

const SelectTestingOptionsForm = ({
    exams,
    specializations,
    initialOptions = {},
    onSelect = () => {}
}) => {
    const [exam, setExam] = useState(examToSelectValue(findExamById(exams, initialOptions.examId*1)));
    const [specialization, setSpecialization] = useState(specializationToSelectValue(findSpecializationById(specializations, initialOptions.specializationId*1)));
    const [mode, setMode] = useState(findTestingModyByValue(initialOptions.mode));
    const [count, setCount] = useState(findQuestionModeByValue(initialOptions.count));

    const examsOptions = exams.map(exam => ({value: exam.id, label: exam.title}));
    const specializationsOptions = specializations.map(specialization => ({value: specialization.id, label: specialization.title}));

    const selected = () => {
        onSelect({
            examId: exam.value,
            specializationId: exam.value,
            mode: mode.value,
            count: count.value
        });
    }

    const isValidOptions = () => {
        return [exam, specialization, mode, count].filter(item => item == null).length !== 0;
    }

    return (
        <div className="SelectTestingOptionsForm s-vflex-center full-width">
            <SingleSelect
                placeholder="Виберіть рік"
                options={examsOptions}
                value={exam}
                onChange={setExam} />
            <Spacer height={20} />
            <SingleSelect
                placeholder="Виберіть спеціалізацію"
                options={specializationsOptions}
                value={specialization}
                onChange={setSpecialization} />
            <Spacer height={20} />
            <SingleSelect
                placeholder="Виберіть режим"
                options={testingModes}
                value={mode}
                onChange={setMode} />
            <Spacer height={20} />
            <SingleSelect
                placeholder="Виберіть кількість питань"
                options={questionsModes}
                value={count}
                onChange={setCount} />
            <Spacer height={20} />
            <div className="s-hflex-center">
                <Button onClick={selected} isFilled={true} disabled={isValidOptions()}>
                    Розпочати
                </Button>
            </div>
        </div>
    );
}

export default SelectTestingOptionsForm;
