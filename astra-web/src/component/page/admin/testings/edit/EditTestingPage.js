import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {changeStatus, getTestingInfo, getTests} from "../../../../../service/testing.service";
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
import Paginated from "../../../../Paginated/Paginated";
import TestingTestsFilter from "../../../../filter/TestingTestsFilter/TestingTestsFilter";
import withTitle from "../../../../hoc/withTitle/withTitle";
import SingleSelect from "../../../../SingleSelect/SingleSelect";
import {testingStatusOptions} from "../../../../../constant/testing.status";
import MessagePopupBody from "../../../../popup-component/MessagePopupBody/MessagePopupBody";

const EditTestingPage = () => {
    const {id} = useParams();
    const [testingInfo, setTestingInfo] = useState(defaultEmptyTesting);
    const [error, setError] = useState(null);
    const [testingStatusState, setTestingStatusState] = useState({loading: false, status: null});

    const fetchTestingInfo = async () => {
        const testingInfo = await getTestingInfo(id);
        setTestingInfo(testingInfo);
        findTestingStatus(testingInfo.status);
    }

    const findTestingStatus = status => {
        const testingStatus = testingStatusOptions.find(item => item.value === status);
        setTestingStatusState(previous => ({
            ...previous,
            status: testingStatus
        }));
    }

    const getPage = async (filter, pageable) => {
        return await getTests(id, filter, pageable);
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

    const applyStatusChange = async setPopupState => {
        setTestingStatusState(previous => ({
            ...previous,
            loading: true
        }));
        const result = await changeStatus(id, testingStatusState.status.value);
        setTestingStatusState(previous => ({
            ...previous,
            loading: false
        }));
        const message = result.errors ? "На жаль, при зміні статусу сталась несподівана помилка. Спробуйте пізніше" : "Зміна статусу пройшла успішно";
        if (result.errors) {
            findTestingStatus(testingInfo.status);
        } else {
            fetchTestingInfo();
        }
        setPopupState({
            bodyGetter: () => <MessagePopupBody message={message} />
        });
    }

    return (
        <div className="container EditTestingPage">
            <div className="row">
                <div className="s-vflex">
                    <div className="info">
                        <InfoHeader className="s-hflex full-width">
                            {
                                testingInfo ? (
                                    <div>{testingInfo.exam.title} - {testingInfo.specialization.title}</div>
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
                                        <div className="s-hflex">
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
                                            <div className="equal-flex" />
                                            <div className="s-vflex">
                                                <SingleSelect
                                                    placeholder="Статус"
                                                    value={testingStatusState.status}
                                                    onChange={value => setTestingStatusState(previous => ({
                                                        ...previous,
                                                        status: value
                                                    }))}
                                                    options={testingStatusOptions} />
                                                <Spacer height={10} />
                                                <div className="s-hflex-end">
                                                    <LoaderBoundary condition={testingStatusState.loading} size="small">
                                                        <PopupConsumer>
                                                            {
                                                                ({setPopupState}) => (
                                                                    <Button isFilled={true} onClick={() => applyStatusChange(setPopupState)}>Зберегти</Button>
                                                                )
                                                            }
                                                        </PopupConsumer>
                                                    </LoaderBoundary>
                                                </div>
                                            </div>
                                        </div>
                                    );
                                },
                                content: ({items, orderFrom, refreshPage}) => <TestsQuestionsTable status={testingInfo.status} tests={items} orderFrom={orderFrom} onDelete={id => deleteTest(id, refreshPage)} />
                            }
                        }
                    </Paginated>
                </div>
            </div>
        </div>
    );
}

export default withTitle(EditTestingPage, "Редагування іспиту");
