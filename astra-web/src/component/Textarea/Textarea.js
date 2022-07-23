import "./Textarea.css";

const Textarea = props => {
    const {
        placeholder,
        withLabel = true,
        ...otherProps
    } = props;

    return (
        <div className="Textarea s-vflex">
            {
                withLabel ? (
                    <label>{placeholder}</label>
                ) : null
            }
            <textarea placeholder={placeholder} {...otherProps} />
        </div>
    );
}

export default Textarea;
