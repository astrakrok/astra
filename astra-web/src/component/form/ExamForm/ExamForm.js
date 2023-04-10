import {useState} from "react";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../Spacer/Spacer";
import ErrorsArea from "../../ErrorsArea/ErrorsArea";
import {create, update} from "../../../service/exam.service";
import {defaultExam} from "../../../data/default/exam";
import {examSchema} from "../../../validation/schema/exam";
import V from "max-validator";
import "./ExamForm.css";
import DisplayBoundary from "../../DisplayBoundary/DisplayBoundary";

const ExamForm = ({
    initialExam = defaultExam,
    onSuccess = () => {}
}) => {
    const [exam, setExam] = useState(initialExam);
    const [formState, setFormState] = useState({loading: false, errors: {}});

    const save = async () => {
        setFormState({
            loading: true,
            errors: {}
        });
        const result = V.validate(exam, examSchema);
        if (result.hasError) {
            setFormState({
                loading: false,
                errors: result.errors
            });
            return;
        }
        const data = exam.id ? await update(exam) : await create(exam);
        if (data.error) {
            setFormState({
                loading: false,
                errors: {
                    global: [data.error]
                }
            });
            return;
        }
        setFormState(previous => ({
            ...previous,
            loading: false
        }));
        onSuccess(data);
    }

    const updateExamTitle = title => {
        setExam(previous => ({
            ...previous,
            title
        }));
    }

    return (
        <div className="s-vflex">
            <Input
                data-test-id="exam-input-field"
                withLabel={false}
                placeholder="Назва іспиту"
                value={exam.title}
                onChange={event => updateExamTitle(event.target.value)} />
            <ErrorsArea errors={formState.errors.title} />
            <Spacer height={20} />
            <div className="s-hflex-center">
                <LoaderBoundary size="small" condition={formState.loading}>
                    <Button isFilled={true} onClick={save} data-test-id="exam-confirm-action">
                        {exam.id ? "Підтвердити" : "Створити"}
                    </Button>
                </LoaderBoundary>
            </div>
            <DisplayBoundary condition={formState.errors.global}>
                <Spacer height={20} />
                <ErrorsArea errors={formState.errors.global} data-test-id="exam-errors" />
            </DisplayBoundary>
        </div>
    );
}

export default ExamForm;
