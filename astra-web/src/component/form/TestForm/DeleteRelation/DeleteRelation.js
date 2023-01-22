import {useState} from "react";
import PopupConsumer from "../../../../context/popup/PopupConsumer";
import {deleteByTestingIdAndTestId} from "../../../../service/testing.test.service";
import LoaderBoundary from "../../../LoaderBoundary/LoaderBoundary";
import ActionDialog from "../../../popup-component/ActionDialog/ActionDialog";
import Tooltipped from "../../../Tooltipped/Tooltipped";
import "./DeleteRelation.css";

const DeleteRelation = ({
    testingId,
    testId,
    onDelete = () => {}
}) => {
    const [loading, setLoading] = useState(false);

    const deleteRelation = async testingId => {
        setLoading(true);
        const result = await deleteByTestingIdAndTestId(testingId, testId);
        await onDelete(result);
        setLoading(false);
    }

    const askToDeleteRelation = (setPopupState, testingId) => {
        setPopupState({
            bodyGetter: () => <ActionDialog setPopupState={setPopupState} message="Ви дійсно хочете видалити даний тест з цього іспиту?" onConfirm={() => deleteRelation(testingId)} />
        });
    }

    return (
        <div className="s-hflex-center">
            <LoaderBoundary condition={false} size="small">
                <Tooltipped tooltip="Видалити" position="top">
                    <LoaderBoundary condition={loading} size="small">
                        <PopupConsumer>
                            {
                                ({setPopupState}) => (
                                    <i className="material-icons clickable delete" onClick={() => askToDeleteRelation(setPopupState, testingId)}>close</i>
                                )
                            }
                        </PopupConsumer>
                    </LoaderBoundary>
                </Tooltipped>
            </LoaderBoundary>
        </div>
    );
}

export default DeleteRelation;
