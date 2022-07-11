import "./Input.css";

const Input = props => {
    const {
        placeholder,
        withLabel = true
    } = props;

    return (
        <div className="Input s-vflex">
            {
                withLabel ? (
                    <label>{placeholder}</label>
                ) : null
            }
            <input type="input" {...props} />
        </div>
    );
}

export default Input;
