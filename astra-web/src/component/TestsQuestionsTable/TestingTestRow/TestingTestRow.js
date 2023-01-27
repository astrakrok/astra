import {useState} from "react";
import {Link} from "react-router-dom";
import {page} from "../../../constant/page";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import DisplayBoundary from "../../DisplayBoundary/DisplayBoundary";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import ActionDialog from "../../popup-component/ActionDialog/ActionDialog";
import Spacer from "../../Spacer/Spacer";
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
            <td>
                <div className="s-hflex-end">
                    <div className="delete">
                        <DisplayBoundary condition={status !== "ACTIVE"}>
                            <LoaderBoundary condition={loading} size="small">
                                <Tooltipped tooltip="Видалити" position="top">
                                    <PopupConsumer>
                                        {
                                            ({setPopupState}) => <i className="material-icons clickable" onClick={() => askToDelete(setPopupState)}>close</i>
                                        }
                                    </PopupConsumer>
                                </Tooltipped>
                            </LoaderBoundary>
                        </DisplayBoundary>
                    </div>
                    <Spacer width={10} />
                    <div className="view">
                        <Link to={page.admin.tests.id(test.id).edit}>
                            <Tooltipped tooltip="Переглянути" position="top">
                                <i className="material-icons clickable">visibility</i>
                            </Tooltipped>
                        </Link>
                    </div>
                </div>
            </td>
        </tr>
    );
}

export default TestingTestRow;
