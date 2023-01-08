import TestForm from "../TestForm/TestForm";
import {saveTest, saveTestDraft} from "../../../service/test.service";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import "./CreateTestForm.css";
import MessagePopupBody from "../../popup-component/MessagePopupBody/MessagePopupBody";

const CreateTestForm = () => {
    const toSendableTest = test => ({
        id: test.id,
        question: test.question,
        questionSvg: test.questionSvg,
        comment: test.comment,
        commentSvg: test.commentSvg,
        variants: test.variants,
        subjectIds: test.subjects.map(item => item.id)
    });

    const save = async (test, setPopupState) => {
        const data = await saveTest(toSendableTest(test));
        const message = data.id ? "Тест успішно створений" : "Під час створення тесту виникла помилка :(";
        setPopupState({
            bodyGetter: () => <MessagePopupBody message={message} />
        });
        return {
            ...data,
            id: null
        };
    }

    const saveDraft = async (test, setPopupState) => {
        const data = await saveTestDraft(toSendableTest(test));
        const message = data.id ? "Тест-чернетка успішно створений" : "Під час створення чернетки виникла помилка :(";
        setPopupState({
            bodyGetter: () => <MessagePopupBody message={message} />
        });
        return {
            ...data,
            id: null
        };
    }

    return (
        <PopupConsumer>
            {
                ({setPopupState}) => (
                    <TestForm
                        onSend={test => save(test, setPopupState)}
                        onSendDraft={test => saveDraft(test, setPopupState)} />
                )
            }
        </PopupConsumer>
    );
}

export default CreateTestForm;
