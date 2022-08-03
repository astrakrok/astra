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
    onConfirm = () => {}
}) => {
    const [status, setStatus] = useState(loadingStatus.inProgress);

    const confirmAction = async () => {
        setStatus(loadingStatus.loading);
        await onConfirm();
        setPopupState();
    }

    return (
        <div className="ActionDialog s-vflex-center">
            <div className="message">
                {message}
            </div>
            <div className="actions s-hflex-center">
                <DisplayBoundary condition={status !== loadingStatus.loading}>
                    <Button isFilled={false} onClick={() => setPopupState()}>
                        Скасувати
                    </Button>
                    <Spacer width={20} />
                </DisplayBoundary>
                <LoaderBoundary condition={status === loadingStatus.loading} size="small">
                    <Button isFilled={true} onClick={() => confirmAction()}>
                        Підтвердити
                    </Button>
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default ActionDialog;
