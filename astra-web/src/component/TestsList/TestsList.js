import PopupConsumer from "../../context/popup/PopupConsumer";
import {deleteTest} from "../../service/test.service";
import Table from "../Table/Table";
import TestRow from "./TestRow/TestRow";
import MessagePopupBody from "../popup-component/MessagePopupBody/MessagePopupBody";
import "./TestsList.css";

const TestsList = ({
    tests,
    orderFrom = 1,
    onDelete = () => {}
}) => {
    const mapErrorsToMessage = errors => {
        for (const error of errors) {
            switch (error.type) {
                case "INVALID_STATUS":
                    return "Тест не може бути видалений, оскільки він не є чернеткою";
                default:
                    return "На жаль, тест не вдалося видалити з технічних причин. Спробуйте пізніше";
            }
        }
    }

    const tryDelete = async (testId, setPopupState) => {
        const result = await deleteTest(testId);
        if (result.errors) {
            const message = mapErrorsToMessage(result.errors);
            setPopupState({
                bodyGetter: () => <MessagePopupBody message={message} />
            });
        } else {
            await onDelete();
        }
    }

    const renderRowWithPopup = (test, index) => (
        <PopupConsumer key={test.id}>
            {
                ({setPopupState}) => (
                    <TestRow index={orderFrom + index} test={test} onDelete={testId => tryDelete(testId, setPopupState)} setPopupState={setPopupState} />
                )
            }
        </PopupConsumer>
    );

    return (
        <Table className="TestsList" type="secondary">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Питання</th>
                    <th>Спеціалізації</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
            {
                tests.map(renderRowWithPopup)
            }
            </tbody>
        </Table>
    );
}

export default TestsList;
