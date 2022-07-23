import Select from "react-select";
import "./MultiSelect.css";

const MultiSelect = props => {
    const {
        className = "",
        emptyMessage = "No options",
        ...otherProps
    } = props;

    return (
        <Select
            className={`MultiSelect ${className}`}
            isMulti={true}
            noOptionsMessage={() => emptyMessage}
            {...otherProps} />
    );
}

export default MultiSelect;
