import "./Input.css";

export const Input = props => {
    return (
        <div className="Input s-vflex">
            <label>{props.placeholder}</label>
            <input type="input" {...props} />
        </div>
    );
}
