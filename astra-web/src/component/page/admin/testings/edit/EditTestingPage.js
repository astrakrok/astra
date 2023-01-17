import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {activateTesting, getTestingInfo, getTests} from "../../../../../service/testing.service";
import {create, deleteById} from "../../../../../service/testing.test.service";
import Button from "../../../../Button/Button";
import InfoHeader from "../../../../InfoHeader/InfoHeader";
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
import Paginated from "../../../../Paginated/Paginated";
import TestingTestsFilter from "../../../../filter/TestingTestsFilter/TestingTestsFilter";
import withTitle from "../../../../hoc/withTitle/withTitle";

const EditTestingPage = () => {
    const {id} = useParams();
    const [testingInfo, setTestingInfo] = useState(defaultEmptyTesting);
    const [error, setError] = useState(null);
    const [activationState, setActivationState] = useState({loading: false, errors: []});

    const fetchTestingInfo = async () => {
        const testingInfo = await getTestingInfo(id);
        setTestingInfo(testingInfo);
    }

    const getPage = async (filter, pageable) => {
        return await getTests(id, filter, pageable);
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
    }, []);

    const deleteTest = async (testingTestId, refreshPage) => {
        const result = await deleteById(testingTestId);
        if (!result.error) {
            fetchTestingInfo();
            refreshPage();
        }
    }

    const renderError = (error, index) => (
        <div key={index} className="error">
            {
                error.type === "EMPTY" ? "Іспит не містить тестів" : "Невідома помилка"
            }
        </div>
    );

    const addTestToTesting = async (test, setPopupState, setFilter) => {
        const data = {
            testingId: id,
            testId: test.id
        };
        const result = await create(data);
        if (result.id) {
            setFilter({});
            fetchTestingInfo();
        } else {
            setError(errorMessage.UNABLE_TO_ADD_TESTING_TEST);
        }
        setPopupState();
    }

    const openAddTestForm = (setPopupState, setFilter) => {
        setPopupState({
            size: "medium",
            bodyGetter: () => (
                <AddTestToTestingForm
                    testingId={id}
                    onSelected={test => addTestToTesting(test, setPopupState, setFilter)}/>
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
                    </div>
                    <DisplayBoundary condition={activationState.errors.length > 0}>
                        <Spacer height={10} />
                        <div className="activation-errors s-hflex-end">
                            {
                                activationState.errors.map(renderError)
                            }
                        </div>
                    </DisplayBoundary>
                    <Spacer height={10}/>
                    <div className="info">
                        <InfoHeader className="s-hflex full-width">
                            {
                                testingInfo ? (
                                    <div>{testingInfo.status === "DRAFT" ? "Чернетка | " : null}{testingInfo.exam.title} - {testingInfo.specialization.title}</div>
                                ) : null
                            }
                            <div className="equal-flex"/>
                            <div>Кількість тестів - {testingInfo.testsCount}</div>
                        </InfoHeader>
                    </div>
                    <DisplayBoundary condition={error != null}>
                        <FormError message={error} className="center weight-strong" />
                        <Spacer height={10} />
                    </DisplayBoundary>
                    <Paginated pageHandler={getPage}>
                        {
                            {
                                filter: ({setFilter}) => {
                                    return (
                                        <div className="s-vflex">
                                            <TestingTestsFilter onFilterSelected={setFilter} />
                                            <Spacer height={10} />
                                            <PopupConsumer>
                                                {
                                                    ({setPopupState}) => (
                                                        <Button isFilled={true} onClick={() => openAddTestForm(setPopupState, setFilter)}>
                                                            Додати тест
                                                        </Button>
                                                    )
                                                }
                                            </PopupConsumer>
                                        </div>
                                    );
                                },
                                content: ({items, orderFrom, refreshPage}) => <TestsQuestionsTable tests={items} orderFrom={orderFrom} onDelete={id => deleteTest(id, refreshPage)} />
                            }
                        }
                    </Paginated>
                </div>
            </div>
        </div>
    );
}

export default withTitle(EditTestingPage, "Редагування іспиту");
