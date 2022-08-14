import TestForm from "../TestForm/TestForm";
import {saveTest} from "../../../service/test.service";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import "./CreateTestForm.css";
import MessagePopupBody from "../../popup-component/MessagePopupBody/MessagePopupBody";

const CreateTestForm = () => {
    const save = async (test, setPopupState) => {
        const data = await saveTest({
            id: test.id,
            question: test.question,
            comment: test.comment,
            variants: test.variants,
            subjectIds: test.subjects.map(item => item.id)
        });

        const message = data.id ? "Тест успішно створений" : "Під час створення тесту виникла помилка :(";

        setPopupState({
            bodyGetter: () => <MessagePopupBody message={message} />
        });
    }

    return (
        <PopupConsumer>
            {
                ({setPopupState}) => (
                    <TestForm onSend={test => save(test, setPopupState)} />
                )
            }
        </PopupConsumer>
    );
}

export default CreateTestForm;
