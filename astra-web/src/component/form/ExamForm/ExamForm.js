import {useState} from "react";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../Spacer/Spacer";
import {create} from "../../../service/exam.service";
import "./ExamForm.css";

const ExamForm = ({
    exam = {},
    onSuccess = () => {}
}) => {
    const [title, setTitle] = useState(exam.title ? exam.title : "");
    const [loading, setLoading] = useState(false);

    const save = async () => {
        setLoading(true);
        const data = await create({title});
        setLoading(false);
        onSuccess(data);
    }

    return (
        <div className="s-vflex">
            <Input
                withLabel={false}
                placeholder="Назва іспиту"
                value={title}
                onChange={event => setTitle(event.target.value)} />
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
