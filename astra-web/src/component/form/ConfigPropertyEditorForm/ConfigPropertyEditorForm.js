import {useState} from "react";
import {message} from "../../../constant/message";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import {update} from "../../../service/property.service";
import Button from "../../Button/Button";
import Editor from "../../Editor/Editor";
import InfoHeader from "../../InfoHeader/InfoHeader";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import MessagePopupBody from "../../popup-component/MessagePopupBody/MessagePopupBody";
import Spacer from "../../Spacer/Spacer";
import "./ConfigPropertyEditorForm.css";

const ConfigPropertyEditorForm = ({
                                      initialValue = "",
                                      propertyName = "",
                                      propertyHeader = ""
                                  }) => {
    const [value, setValue] = useState(initialValue);
    const [loading, setLoading] = useState(false);

    const updateProperty = async setPopupState => {
        setLoading(true);
        const result = await update({
            name: propertyName,
            value: value
        });
        setLoading(false);
        if (result.error) {
            setPopupState({
                bodyGetter: () => <MessagePopupBody message={result.error}/>
            });
        } else {
            setPopupState({
                bodyGetter: () => <MessagePopupBody message={message.propertyUpdateSucceed}/>
            });
        }
    }

    return (
        <div className="ConfigPropertyEditorForm s-vflex">
            <InfoHeader>{propertyHeader}</InfoHeader>
            <Editor
                value={value}
                onChange={setValue}/>
            <Spacer height={10}/>
            <div className="s-hflex-end">
                <LoaderBoundary condition={loading} size="small">
                    <PopupConsumer>
                        {
                            ({setPopupState}) => (
                                <Button isFilled={true} onClick={() => updateProperty(setPopupState)}>
                                    Зберегти
                                </Button>
                            )
                        }
                    </PopupConsumer>
                </LoaderBoundary>
            </div>
        </div>
    );
}

export default ConfigPropertyEditorForm;
