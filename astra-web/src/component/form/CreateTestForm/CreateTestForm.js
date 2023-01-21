import TestForm from "../TestForm/TestForm";
import {saveTest, saveTestDraft} from "../../../service/test.service";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import "./CreateTestForm.css";
import MessagePopupBody from "../../popup-component/MessagePopupBody/MessagePopupBody";
import {Link} from "react-router-dom";
import {page} from "../../../constant/page";
import useRefresh from "../../../hook/useRefresh";
import {defaultEmptyTest} from "../../../data/default/test";

const CreateTestForm = () => {
    const [value, refresh] = useRefresh();

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
        const message = data.id ? (
            <div>Тест успішно створений. Ви можете <a href={void(0)} onClick={() => emptyValues(setPopupState)} className="clickable">створити ще один тест</a> або <Link to={page.admin.tests.id(data.id).edit} target="_blank">перейти до редагування даного</Link></div>
        ) : "Під час створення тесту виникла помилка :(";
        setPopupState({
            closeOnOutsideClick: false,
            bodyGetter: () => <MessagePopupBody message={message} />
        });
        return data;
    }

    const emptyValues = setPopupState => {
        refresh();
        setPopupState();
    }

    const saveDraft = async (test, setPopupState) => {
        const data = await saveTestDraft(toSendableTest(test));
        const message = data.id ? (
            <div>Тест-чернетка успішно створений. Ви можете <a href={void(0)} onClick={() => emptyValues(setPopupState)} className="clickable">створити ще один тест</a> або <Link to={page.admin.tests.id(data.id).edit} target="_blank">перейти до редагування даного</Link></div>
        ) : "Під час створення чернетки виникла помилка :(";
        setPopupState({
            closeOnOutsideClick: false,
            bodyGetter: () => <MessagePopupBody message={message} />
        });
        return {
            ...data,
            id: null
        };
    }

    return (
        <PopupConsumer key={value}>
            {
                ({setPopupState}) => (
                    <TestForm
                        initialTest={defaultEmptyTest}
                        onSend={test => save(test, setPopupState)}
                        onSendDraft={test => saveDraft(test, setPopupState)} />
                )
            }
        </PopupConsumer>
    );
}

export default CreateTestForm;
