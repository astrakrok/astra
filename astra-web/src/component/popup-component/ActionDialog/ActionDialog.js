import {useState} from "react";
import {loadingStatus} from "../../../constant/loading.status";
import Button from "../../Button/Button";
import DisplayBoundary from "../../DisplayBoundary/DisplayBoundary";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../Spacer/Spacer";
import "./ActionDialog.css";

const ActionDialog = ({
    message,
    setPopupState,
    dataTestId = "",
    onConfirm = () => {}
}) => {
    const [status, setStatus] = useState(loadingStatus.inProgress);

    const confirmAction = async () => {
        setStatus(loadingStatus.loading);
        setPopupState();
        await onConfirm();
    }

    return (
        <div className="ActionDialog s-vflex-center" data-test-id={dataTestId}>
            <div className="message">
                {message}
            </div>
            <div className="actions s-hflex-center">
                <DisplayBoundary condition={status !== loadingStatus.loading}>
                    <Button isFilled={false} onClick={() => setPopupState()} data-test-id="reject">
                        Скасувати
                    </Button>
                    <Spacer width={20} />
                </DisplayBoundary>
                <LoaderBoundary condition={status === loadingStatus.loading} size="small">
                    <Button isFilled={true} onClick={() => confirmAction()} data-test-id="confirm">
                        Підтвердити
                    </Button>
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default ActionDialog;
