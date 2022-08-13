import {useEffect, useState} from "react"
import PopupContext from "./PopupContext";
import Popup from "../../component/Popup/Popup";
import {useLocation} from "react-router-dom";

const PopupProvider = props => {
    const [popupState, setPopupState] = useState();
    const location = useLocation();

    useEffect(() => {
        setPopupState();
    }, [location]);

    return (
        <PopupContext.Provider value={{setPopupState}} {...props}>
            {props.children}
            {popupState && <Popup {...popupState} />}
        </PopupContext.Provider>
    );
}

export default PopupProvider;
