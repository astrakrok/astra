import DisplayBoundary from "../DisplayBoundary/DisplayBoundary";
import FormError from "../FormError/FormError";
import "./ErrorsArea.css";

const ErrorsArea = ({errors}) => {
    return (
        <DisplayBoundary condition={errors}>
            <div className="s-vflex">
                {
                    errors ? errors.map((message, index) => <FormError key={index} message={message} />) : null
                }
            </div>
        </DisplayBoundary>
    );
}

export default ErrorsArea;
