import {useState} from "react";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import Spacer from "../../Spacer/Spacer";
import "./TestingTestsFilter.css";

const TestingTestsFilter = ({
    onFilterSelected = () => {}
}) => {
    const [filter, setFilter] = useState({
        searchText: ""
    });

    return (
        <div className="s-hflex full-width">
            <Input
                wrapperClassName="1equal-flex"
                className="full-height"
                placeholder="Пошук по тексту"
                value={filter.searchText}
                withLabel={false}
                onChange={event => setFilter(previous => ({
                    ...previous,
                    searchText: event.target.value
                }))} />
            <Spacer width={20} />
            <Button onClick={() => onFilterSelected(filter)}>Шукати</Button>
        </div>
    );
}

export default TestingTestsFilter;
