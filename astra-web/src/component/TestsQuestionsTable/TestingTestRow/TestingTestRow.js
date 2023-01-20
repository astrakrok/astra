import {useState} from "react";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import DisplayBoundary from "../../DisplayBoundary/DisplayBoundary";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import ActionDialog from "../../popup-component/ActionDialog/ActionDialog";
import Tooltipped from "../../Tooltipped/Tooltipped";
import "./TestingTestRow.css";

const TestingTestRow = ({
                            order = 1,
                            status = 'DRAFT',
                            onDelete = () => {
                            },
                            test
                        }) => {
    const [loading, setLoading] = useState(false);

    const handleDelete = async () => {
        setLoading(true);
        await onDelete(test.id);
        setLoading(false);
    }

    const askToDelete = setPopupState => {
        const message = "Ви дійсно хочете видалити цей тест зі списку?";
        setPopupState({
            bodyGetter: () => <ActionDialog setPopupState={setPopupState} message={message} onConfirm={handleDelete} />
        });
    }

    return (
        <tr>
            <td className="center">{order}</td>
            <td className="line-break">{test.testQuestion}</td>
            <DisplayBoundary condition={status === 'ACTIVE'}>
                <td>
                    <div className="delete s-hflex-center">
                        <LoaderBoundary condition={loading} size="small">
                            <Tooltipped tooltip="Видалити" position="top">
                                <PopupConsumer>
                                    {
                                        ({setPopupState}) => <i className="material-icons clickable" onClick={() => askToDelete(setPopupState)}>close</i>
                                    }
                                </PopupConsumer>
                            </Tooltipped>
                        </LoaderBoundary>
                    </div>
                </td>
            </DisplayBoundary>
        </tr>
    );
}

export default TestingTestRow;
