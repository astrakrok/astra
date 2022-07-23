import Select from "react-select";
import "./SingleSelect.css";

const SingleSelect = props => {
    const {
        className,
        emptyMessage = "",
        ...otherProps
    } = props;

    return (
        <Select
            className={`SingleSelect ${className}`}
            noOptionsMessage={() => emptyMessage}
            {...otherProps} />
    );
}

export default SingleSelect;
