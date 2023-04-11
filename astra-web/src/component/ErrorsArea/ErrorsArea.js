import DisplayBoundary from "../DisplayBoundary/DisplayBoundary";
import FormError from "../FormError/FormError";
import "./ErrorsArea.css";

const ErrorsArea = (props) => {
    const {
        errors,
        ...other
    } = props;
    return (
        <DisplayBoundary condition={errors}>
            <div className="s-vflex" {...other}>
                {
                    errors ? errors.map((message, index) => <FormError key={index} message={message} />) : null
                }
            </div>
        </DisplayBoundary>
    );
}

export default ErrorsArea;
