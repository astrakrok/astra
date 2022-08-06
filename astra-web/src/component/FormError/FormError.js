import "./FormError.css";

const FormError = ({message}) => {
    return (
        <div className="FormError">
            {message}
        </div>
    );
}

export default FormError;
