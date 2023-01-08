import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {activateTesting, getTestingInfo, getTests} from "../../../../../service/testing.service";
import {create, deleteById} from "../../../../../service/testing.test.service";
import Button from "../../../../Button/Button";
import InfoHeader from "../../../../InfoHeader/InfoHeader";
import InfoText from "../../../../InfoText/InfoText";
import LoaderBoundary from "../../../../LoaderBoundary/LoaderBoundary";
import DisplayBoundary from "../../../../DisplayBoundary/DisplayBoundary";
import Spacer from "../../../../Spacer/Spacer";
import TestsQuestionsTable from "../../../../TestsQuestionsTable/TestsQuestionsTable";
import PopupConsumer from "../../../../../context/popup/PopupConsumer";
import "./EditTestingPage.css";
import AddTestToTestingForm from "../../../../form/AddTestToTestingForm/AddTestToTestingForm";
import FormError from "../../../../FormError/FormError";
import {errorMessage} from "../../../../../error/message";
import {defaultEmptyTesting} from "../../../../../data/default/testing";
import ActionDialog from "../../../../popup-component/ActionDialog/ActionDialog";

const EditTestingPage = () => {
    const {id} = useParams();
    const [testingInfo, setTestingInfo] = useState(defaultEmptyTesting);
    const [formState, setFormState] = useState({});
    const [activationState, setActivationState] = useState({loading: false, errors: []});

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

    const makeActivation = async () => {
        setActivationState(previous => ({...previous, loading: true}));
        const result = await activateTesting(testingInfo.id);
        if (result.id) {
            setTestingInfo(result);
            setActivationState(previous => ({...previous, loading: false}));
        } else {
            setActivationState(previous => ({...previous, loading: false, errors: result.errors}));
        }
    }

    const activate = async setPopupState => {
        const message = "Ви дійсно бажаєте активувати іспит? Дану операцію неможливо буде відмінити";
        setPopupState({
            bodyGetter: () => <ActionDialog message={message} setPopupState={setPopupState} onConfirm={makeActivation} />
        })
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

    const renderError = (error, index) => (
        <div key={index} className="error">
            {
                error.type === "EMPTY" ? "Іспит не містить тестів" : "Невідома помилка"
            }
        </div>
    );

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
        <div className="container EditTestingPage">
            <div className="row">
                <div className="s-vflex">
                    <div className="create s-hflex-end">
                        <DisplayBoundary condition={testingInfo.status !== "ACTIVE"}>
                            <LoaderBoundary condition={activationState.loading} size="small">
                                <PopupConsumer>
                                    {
                                        ({setPopupState}) => (
                                            <Button onClick={() => activate(setPopupState)}>Активувати</Button>
                                        )
                                    }
                                </PopupConsumer>
                            </LoaderBoundary>
                        </DisplayBoundary>
                        <Spacer width={15} />
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
                    <DisplayBoundary condition={activationState.errors.length > 0}>
                        <Spacer height={10} />
                        <div className="activation-errors s-hflex-end">
                            {
                                activationState.errors.map(renderError)
                            }
                        </div>
                    </DisplayBoundary>
                    <Spacer height={20}/>
                    <div className="info">
                        <InfoHeader className="s-hflex full-width">
                            {
                                testingInfo ? (
                                    <div>{testingInfo.status === "DRAFT" ? "Чернетка | " : null}{testingInfo.exam.title} - {testingInfo.specialization.title}</div>
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
