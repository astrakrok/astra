import CreateTestForm from "../../../../form/CreateTestForm/CreateTestForm";
import withTitle from "../../../../hoc/withTitle/withTitle";
import "./CreateTestPage.css";

const CreateTestPage = () => {
    return (
        <>
            <div className="CreateTestPage container">
                <div className="row">
                    <CreateTestForm />
                </div>
            </div>
            <div className="equal-flex" />
        </>
    );
}

export default withTitle(CreateTestPage, "Створити тест");
