import {useState} from "react";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import Spacer from "../../Spacer/Spacer";
import "./ImportHistoryFilter.css";

const ImportHistoryFilter = ({
    onFilterSelected = () => {}
}) => {
    const [searchText, setSearchText] = useState("");

    return (
        <div className="s-hflex full-width">
            <Input
                wrapperClassName="full-height"
                className="full-height"
                placeholder="Пошук по тексту"
                withLabel={false}
                value={searchText}
                onChange={event => setSearchText(event.target.value)} />
            <Spacer width={20} />
            <Button onClick={() => onFilterSelected({searchText})}>Шукати</Button>
        </div>
    )
}

export default ImportHistoryFilter;
