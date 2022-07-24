import {useState} from "react";
import Button from "../../Button/Button";
import SingleSelect from "../../SingleSelect/SingleSelect";
import Spacer from "../../Spacer/Spacer";
import "./SelectExams.css";

const SelectExams = ({
    exams,
    selectedExams = [],
    onSave = () => {}
}) => {
    const [selected, setSelected] = useState({});

    const selectedIds = selectedExams.map(item => item.id);
    const options = exams.filter(item => !selectedIds.includes(item.id)).map(item => ({value: item.id, label: item.title}));

    const save = () => {
        if (selected.value) {
            onSave({
                id: selected.value,
                title: selected.label
            });
        }
    }

    return (
        <div className="SelectExams s-vflex">
            <SingleSelect
                placeholder="Виберіть екзамен"
                emptyMessage="Немає доступних екзаменів"
                onChange={setSelected}
                options={options} />
            <Spacer height={20}/>
            <div className="s-hflex-center">
                <Button isFilled={true} onClick={save}>
                    Зберегти
                </Button>
            </div>
        </div>
    );
}

export default SelectExams;
