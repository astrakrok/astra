import TestForm from "../TestForm/TestForm";
import "./EditTestForm.css";

const EditTestForm = ({test}) => {
    return (
        <TestForm initialTest={test} />
    );
}

export default EditTestForm;
