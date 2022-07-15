import { useState} from "react" 
import PopupContext from "./PopupContext";
import Popup from "../../component/Popup/Popup";

const PopupProvider = props => {
    const [popupState, setPopupState] = useState();

    return (
        <PopupContext.Provider value={{setPopupState}} {...props}>
            {props.children}
            {popupState && <Popup {...popupState} />}
        </PopupContext.Provider>
    );
}

export default PopupProvider;
