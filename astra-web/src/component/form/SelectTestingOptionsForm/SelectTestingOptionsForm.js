import {useEffect, useState} from "react";
import {testingModes} from "../../../constant/testing.mode";
import {questionsModes} from "../../../constant/questions.mode";
import SingleSelect from "../../SingleSelect/SingleSelect";
import Button from "../../Button/Button";
import Spacer from "../../Spacer/Spacer";
import DisplayBoundary from "../../DisplayBoundary/DisplayBoundary";
import "./SelectTestingOptionsForm.css";
import InfoHeader from "../../InfoHeader/InfoHeader";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import {getExamTestings} from "../../../service/testing.service";

const SelectTestingOptionsForm = ({
                                      exams,
                                      onSelect = () => {
                                      }
                                  }) => {
    const [exam, setExam] = useState(null);
    const [selectedTesting, setSelectedTesting] = useState(null);
    const [mode, setMode] = useState();
    const [count, setCount] = useState();
    const [testings, setTestings] = useState([]);

    const examsOptions = exams.map(exam => ({value: exam.id, label: exam.title}));

    const fetchTestings = async () => {
        setTestings(null);
        const testings = await getExamTestings(exam.value);
        setTestings(testings);
    }

    useEffect(() => {
        if (!exam) {
            return;
        }
        setSelectedTesting(null);
        fetchTestings();
    }, [exam]);

    const selected = event => {
        event.preventDefault();
        const options = {
            testingId: selectedTesting.value,
            mode: mode.value
        };
        if (options.mode === "training") {
            options.count = count.value;
        }
        onSelect(options);
    }

    const isValidOptions = () => {
        const isValidMainData = [selectedTesting, mode].filter(item => item == null).length === 0;
        return isValidMainData && (mode.value === "examination" || (mode.value === "training" && count != null));
    }

    const getTestingsOptions = () => {
        return testings == null ? null : testings.map(item => ({
            label: item.specialization.title,
            value: item.id
        }));
    }

    const updateExam = newExam => {
        if (!newExam && !exam) {
            return;
        } else if (newExam && exam) {
            if (newExam.value !== exam.value) {
                setExam(newExam);
            }
            return;
        }
        setExam(newExam);
    }

    return (
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
                    options={examsOptions}
                    onChange={updateExam}/>
                <Spacer height={20}/>
                <LoaderBoundary condition={testings == null} className="s-hflex-center" size="small">
                    <SingleSelect
                        placeholder="Виберіть спеціалізацію"
                        options={getTestingsOptions()}
                        onChange={setSelectedTesting}/>
                </LoaderBoundary>
                <Spacer height={40}/>
                <div className="s-hflex-center">
                    <Button isFilled={true} disabled={!isValidOptions()} isSubmit="submit">
                        Розпочати
                    </Button>
                </div>
            </form>
        </div>
    );
}

export default SelectTestingOptionsForm;
