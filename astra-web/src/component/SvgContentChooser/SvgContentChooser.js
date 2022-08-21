import {getFirstOrNull, hasExtension, readContentOrNull} from "../../handler/file.handler";
import FileChooser from "../FileChooser/FileChooser";
import Spacer from "../Spacer/Spacer";
import Tooltipped from "../Tooltipped/Tooltipped";
import MessagePopupBody from "../popup-component/MessagePopupBody/MessagePopupBody";
import PopupConsumer from "../../context/popup/PopupConsumer";
import "./SvgContentChooser.css";
import {message} from "../../constant/message";

const SvgContentChooser = ({
                               value,
                               setValue
                           }) => {
    const updateSvg = (event, setPopupState) => {
        const file = getFirstOrNull(event.target.files);
        if (!file || !hasExtension(file.name, "svg")) {
            setValue(null);
            setPopupState({
                bodyGetter: () => <MessagePopupBody message={message.invalidFile}/>
            });
            return;
        }
        readContentOrNull(
            file,
            content => setValue(content)
        );
    }

    return (
        <div className="SvgContentChooser s-hflex">
            <PopupConsumer>
                {
                    ({setPopupState}) => (
                        <FileChooser
                            accept=".svg"
                            title={value ? "Змінити SVG" : "Вибрати SVG"}
                            onSelect={event => updateSvg(event, setPopupState)}/>
                    )
                }
            </PopupConsumer>
            <Spacer width={5}/>
            <div className="clear s-vflex-center">
                <Tooltipped position="top" tooltip="Очистити" className="s-vflex-center">
                    <i className="material-icons clickable" onClick={() => setValue(null)}>delete</i>
                </Tooltipped>
            </div>
        </div>
    );
}

export default SvgContentChooser;
