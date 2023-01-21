import PopupConsumer from "../../context/popup/PopupConsumer";
import "./Popup.css";

const Popup = ({
    size = "tiny",
    closeOnOutsideClick = true,
    headerGetter = () => <></>,
    bodyGetter = () => <></>,
    footerGetter = () => <></>
}) => {
    const handleOutsideClick = (event, setPopupState) => {
        if (!closeOnOutsideClick) {
            return;
        }
        if (event.target !== event.currentTarget) {
            return;
        }
        setPopupState();
    }

    return (
        <PopupConsumer>
            {
                ({setPopupState}) => {
                    return (
                        <div className="Popup popup open" onClick={event => handleOutsideClick(event, setPopupState)}>
                            <div className="popup-wrapper full-height full-width s-vflex-center" onClick={event => handleOutsideClick(event, setPopupState)}>
                                <div className={`popup-container ${size}`}>
                                    <div className="popup-header">
                                        {headerGetter(setPopupState)}
                                    </div>
                                    <div className="popup-body">
                                        {bodyGetter(setPopupState)}
                                    </div>
                                    <div className="popup-footer">
                                        {footerGetter(setPopupState)}
                                    </div>
                                </div>
                            </div>
                        </div>
                    );
                }
            }
        </PopupConsumer>
    );
}

export default Popup;
