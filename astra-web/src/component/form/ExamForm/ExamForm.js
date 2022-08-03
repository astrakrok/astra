import {useState} from "react";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../Spacer/Spacer";
import {create, update} from "../../../service/exam.service";
import {defaultExam} from "../../../data/default/exam";
import "./ExamForm.css";

const ExamForm = ({
    initialExam = defaultExam,
    onSuccess = () => {}
}) => {
    const [exam, setExam] = useState(initialExam);
    const [loading, setLoading] = useState(false);

    const save = async () => {
        setLoading(true);
        const data = exam.id ? await update(exam) : await create();
        setLoading(false);
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
                withLabel={false}
                placeholder="Назва іспиту"
                value={exam.title}
                onChange={event => updateExamTitle(event.target.value)} />
            <Spacer height={20} />
            <div className="s-hflex-center">
                <LoaderBoundary size="small" condition={loading}>
                    <Button isFilled={true} onClick={save}>
                        {exam.id ? "Підтвердити" : "Створити"}
                    </Button>
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default ExamForm;
