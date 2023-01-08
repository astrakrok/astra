import {useState} from "react";
import {useSearchParams} from "react-router-dom";
import Button from "../../Button/Button";
import Input from "../../Input/Input";
import SingleSelect from "../../SingleSelect/SingleSelect";
import Spacer from "../../Spacer/Spacer";
import "./TestsFilter.css";

const statusOptions = [
    {label: "Усі", value: null},
    {label: "Активні", value: 'ACTIVE'},
    {label: "Чернетки", value: 'DRAFT'},
];

const TestsFilter = ({
    initialFilter = {},
    onFilterSelected = () => {}
}) => {
    const [searchParams] = useSearchParams();
    const [filter, setFilter] = useState({
        status: statusOptions[0],
        searchText: "",
        ...initialFilter
    });

    return (
        <div className="s-hflex full-width">
            <SingleSelect
                className="equal-flex"
                value={filter.status}
                placeholder="Виберіть статус"
                onChange={value => setFilter(previous => ({
                    ...previous,
                    status: value
                }))}
                options={statusOptions}/>
            <Spacer width={20} />
            <Input
                wrapperClassName="equal-flex"
                className="full-height"
                placeholder="Пошук по тексту"
                value={filter.searchText}
                withLabel={false}
                onChange={event => setFilter(previous => ({
                    ...previous,
                    searchText: event.target.value
                }))} />
            <Spacer width={20} />
            <Button onClick={() => onFilterSelected({
                ...filter,
                status: filter.status.value,
                importId: searchParams.get("importId")
            })}>Шукати</Button>
        </div>
    );
}

export default TestsFilter;
