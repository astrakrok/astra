import PopupConsumer from "../../../context/popup/PopupConsumer";
import {updateTest} from "../../../service/test.service";
import MessagePopupBody from "../../popup-component/MessagePopupBody/MessagePopupBody";
import TestForm from "../TestForm/TestForm";
import "./EditTestForm.css";

const EditTestForm = ({test}) => {
    const update = async (test, setPopupState) => {
        const data = await updateTest({
            id: test.id,
            question: test.question,
            questionSvg: test.questionSvg,
            comment: test.comment,
            commentSvg: test.commentSvg,
            variants: test.variants,
            subjectIds: test.subjects.map(item => item.id)
        });

        const message = data.error ? "Під час редагування тесту виникла помилка :(" : "Тест успішно відредагований";

        setPopupState({
            bodyGetter: () => <MessagePopupBody message={message}/>
        });
    }

    return (
        <PopupConsumer>
            {
                ({setPopupState}) => (
                    <TestForm onSend={test => update(test, setPopupState)} initialTest={test}/>
                )
            }
        </PopupConsumer>
    );
}

export default EditTestForm;
