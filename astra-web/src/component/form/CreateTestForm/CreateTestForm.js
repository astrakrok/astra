import TestForm from "../TestForm/TestForm";
import {saveTest} from "../../../service/test.service";
import "./CreateTestForm.css";

const CreateTestForm = () => {
    const save = test => {
        saveTest({
            id: test.id,
            question: test.question,
            comment: test.comment,
            variants: test.variants,
            subjectIds: test.subjects.map(item => item.id),
            examIds: test.exams.map(item => item.id)
        });
    }

    return (
        <TestForm onSend={save} />
    );
}

export default CreateTestForm;
