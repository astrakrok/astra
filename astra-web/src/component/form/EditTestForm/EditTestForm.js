import PopupConsumer from "../../../context/popup/PopupConsumer";
import {updateTest, updateTestDraft} from "../../../service/test.service";
import MessagePopupBody from "../../popup-component/MessagePopupBody/MessagePopupBody";
import TestForm from "../TestForm/TestForm";
import "./EditTestForm.css";

const EditTestForm = ({test}) => {
    const toSendableTest = test => ({
        id: test.id,
        question: test.question,
        questionSvg: test.questionSvg,
        comment: test.comment,
        commentSvg: test.commentSvg,
        variants: test.variants,
        subjectIds: test.subjects.map(item => item.id)
    });

    const update = async (test, setPopupState) => {
        const data = await updateTest(toSendableTest(test));

        const message = data.id ? "Тест успішно відредагований" : "Під час редагування тесту виникла помилка :(";

        setPopupState({
            bodyGetter: () => <MessagePopupBody message={message}/>
        });
        return data;
    }

    const updateDraft = async (test, setPopupState) => {
        const data = await updateTestDraft(toSendableTest(test));
        const message = data.id ? "Тест успішно відредагований" : "Під час редагування тесту виникла помилка :(";
        setPopupState({
            bodyGetter: () => <MessagePopupBody message={message}/>
        });
        return data;
    }

    return (
        <PopupConsumer>
            {
                ({setPopupState}) => (
                    <TestForm
                        initialTest={test}
                        onSend={test => update(test, setPopupState)}
                        onSendDraft={test => updateDraft(test, setPopupState)}/>
                )
            }
        </PopupConsumer>
    );
}

export default EditTestForm;
