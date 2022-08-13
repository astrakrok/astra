import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {create, getTestingInfo, getTests} from "../../../../../service/testing.service";
import {deleteById} from "../../../../../service/testing.test.service";
import Button from "../../../../Button/Button";
import InfoHeader from "../../../../InfoHeader/InfoHeader";
import InfoText from "../../../../InfoText/InfoText";
import LoaderBoundary from "../../../../LoaderBoundary/LoaderBoundary";
import Spacer from "../../../../Spacer/Spacer";
import TestsQuestionsTable from "../../../../TestsQuestionsTable/TestsQuestionsTable";
import PopupConsumer from "../../../../../context/popup/PopupConsumer";
import "./EditTestingPage.css";
import AddTestToTestingForm from "../../../../form/AddTestToTestingForm/AddTestToTestingForm";
import FormError from "../../../../FormError/FormError";
import {errorMessage} from "../../../../../error/message";

const EditTestingPage = () => {
    const {id} = useParams();
    const [testingInfo, setTestingInfo] = useState(null);
    const [formState, setFormState] = useState({});

    const fetchTestingInfo = async () => {
        const testingInfo = await getTestingInfo(id);
        setTestingInfo(testingInfo);
    }

    const fetchTests = async () => {
        const tests = await getTests(id);
        setFormState({
            tests: tests,
            error: null
        });
    }

    useEffect(() => {
        fetchTestingInfo();
        fetchTests();
    }, []);

    const deleteTestingTest = async testingTestId => {
        const result = await deleteById(testingTestId);
        if (!result.error) {
            setFormState(previous => ({
                ...previous,
                tests: previous.tests.filter(item => item.id !== testingTestId)
            }))
        }
    }

    const addTestToTesting = async (test, setPopupState) => {
        const data = {
            testingId: id,
            testId: test.id
        };
        const result = await create(data);
        if (result.id) {
            setFormState(previous => ({
                ...previous,
                error: null,
                tests: [
                    ...previous.tests,
                    {
                        id: result.id,
                        test: test
                    }
                ]
            }));
        } else {
            setFormState(previous => ({
                ...previous,
                error: errorMessage.UNABLE_TO_ADD_TESTING_TEST
            }));
        }
        setPopupState();
    }

    const openAddTestForm = setPopupState => {
        setPopupState({
            size: "medium",
            bodyGetter: () => (
                <AddTestToTestingForm
                    testingId={id}
                    onSelected={test => addTestToTesting(test, setPopupState)}/>
            )
        });
    }

    return (
        <div className="container">
            <div className="row">
                <div className="s-vflex">
                    <div className="create s-hflex-end">
                        <PopupConsumer>
                            {
                                ({setPopupState}) => (
                                    <Button isFilled={true} onClick={() => openAddTestForm(setPopupState)}>
                                        Додати тест
                                    </Button>
                                )
                            }
                        </PopupConsumer>
                    </div>
                    <Spacer height={20}/>
                    <div className="info">
                        <InfoHeader className="s-hflex full-width">
                            {
                                testingInfo ? (
                                    <div>{testingInfo.exam.title}({testingInfo.specialization.title})</div>
                                ) : null
                            }
                            <div className="equal-flex"/>
                            <div>Кількість тестів - {formState.tests ? formState.tests.length : 0}</div>
                        </InfoHeader>
                    </div>
                    <div className="tests s-vflex">
                        {
                            formState.error ? (
                                <FormError message={formState.error} className="center weight-strong"/>
                            ) : null
                        }
                        <LoaderBoundary condition={formState.tests == null} className="s-hflex-center">
                            {
                                formState.tests && formState.tests.length > 0 ? (
                                    <TestsQuestionsTable
                                        testingsTests={formState.tests}
                                        onDelete={deleteTestingTest}/>
                                ) : (
                                    <InfoText className="s-hflex-center">Ви поки не додали тестів</InfoText>
                                )
                            }
                        </LoaderBoundary>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EditTestingPage;
