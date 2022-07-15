import "./Input.css";

const Input = props => {
    const {
        placeholder,
        withLabel = true,
        ...otherProps
    } = props;

    return (
        <div className="Input s-vflex">
            {
                withLabel ? (
                    <label>{placeholder}</label>
                ) : null
            }
            <input type="input" placeholder={placeholder} {...otherProps} />
        </div>
    );
}

export default Input;
