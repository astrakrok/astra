import {useState} from "react";
import Button from "../../Button/Button";
import SingleSelect from "../../SingleSelect/SingleSelect";
import Spacer from "../../Spacer/Spacer";
import "./SelectSpecializationForm.css";

const SelectSpecializationForm = ({
                                      specializations,
                                      onSelect = () => {
                                      }
                                  }) => {
    const [selected, setSelected] = useState({});
    const options = specializations.map(item => ({label: `${item.step.title} | ${item.title}`, value: item.id}));

    const confirmed = () => {
        onSelect(selected.value);
    }

    return (
        <div className="SelectSpecializationForm s-hflex full-width">
            <SingleSelect
                placeholder="Виберіть спеціалізацію"
                className="full-width"
                options={options}
                onChange={setSelected}/>
            <Spacer width={10}/>
            <Button isFilled={true} onClick={confirmed}>
                OK
            </Button>
        </div>
    );
}

export default SelectSpecializationForm;
