import "./Textarea.css";

const Textarea = props => {
    const {
        placeholder,
        withLabel = true,
        noMargin = false,
        ...otherProps
    } = props;

    return (
        <div className={`Textarea s-vflex ${noMargin ? "no-margin" : ""}`}>
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
