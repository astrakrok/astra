import Input from "../../Input/Input";
import InfoHeader from "../../InfoHeader/InfoHeader";
import "./ExaminationThresholdForm.css";
import {useState} from "react";
import PopupConsumer from "../../../context/popup/PopupConsumer";
import Button from "../../Button/Button";
import Spacer from "../../Spacer/Spacer";
import LoaderBoundary from "../../LoaderBoundary/LoaderBoundary";
import MessagePopupBody from "../../popup-component/MessagePopupBody/MessagePopupBody";
import {property} from "../../../constant/property";
import {update} from "../../../service/property.service";
import {message} from "../../../constant/message";

const ExaminationThresholdForm = ({
                                      initialValue = "0"
                                  }) => {
    const [value, setValue] = useState(initialValue);
    const [loading, setLoading] = useState(false);

    const saveThreshold = async setPopupState => {
        setLoading(true);
        const data = {
            name: property.examinationThresholdPercentage,
            value: value
        };
        const result = await update(data);
        if (result.error) {
            setPopupState({
                bodyGetter: () => <MessagePopupBody message={result.error}/>
            });
        } else {
            setPopupState({
                bodyGetter: () => <MessagePopupBody message={message.propertyUpdateSucceed}/>
            });
        }
        setLoading(false);
    }

    return (
        <div className="ExaminationThresholdForm s-vflex">
            <InfoHeader>
                Екзаменаційний поріг (%)
            </InfoHeader>
            <Input
                type="number"
                min="0"
                max="200"
                className="browser-default"
                placeholder="Введіть значення"
                withLabel={false}
                value={value}
                onChange={event => setValue(event.target.value)}/>
            <Spacer height={10}/>
            <div className="s-hflex-end">
                <LoaderBoundary condition={loading} size="small">
                    <PopupConsumer>
                        {
                            ({setPopupState}) => (
                                <Button isFilled={true} onClick={() => saveThreshold(setPopupState)}>
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

export default ExaminationThresholdForm;
