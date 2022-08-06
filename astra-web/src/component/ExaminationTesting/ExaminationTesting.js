import PopupConsumer from "../../context/popup/PopupConsumer";
import Timer from "../Timer/Timer";
import MessagePopupBody from "../popup-component/MessagePopupBody/MessagePopupBody";
import "./ExaminationTesting.css";
import {useEffect, useState} from "react";
import ExaminationTest from "../ExaminationTest/ExaminationTest";
import Button from "../Button/Button";
import DisplayBoundary from "../DisplayBoundary/DisplayBoundary";
import {getResult, updateAnswer} from "../../service/examination.service";
import TestingStatistic from "../TestingStatistic/TestingStatistic";
import TestingCorrectness from "../TestingCorrectness/TestingCorrectness";
import Spacer from "../Spacer/Spacer";
import InfoHeader from "../InfoHeader/InfoHeader";
import TestingControl from "../TestingControl/TestingControl";
import {useSearchParams} from "react-router-dom";
import useRefresh from "../../hook/useRefresh";
import TestingNavigation from "../TestingNavigation/TestingNavigation";
import {mapExaminationToNavigationItem} from "../../mapper/test.mapper";
import TestCompletionWarning from "../popup-component/TestCompletionWarning/TestCompletionWarning";
import LoaderBoundary from "../LoaderBoundary/LoaderBoundary";
import {loadingStatus} from "../../constant/loading.status";
import InfoText from "../InfoText/InfoText";

const mapToTestState = test => ({
    ...test,
    lastProcessed: null,
    userAnswer: null
});

const initialResult = {
    status: loadingStatus.inProgress,
    data: null
};

const ExaminationTesting = ({
    tests,
    duration
}) => {
    const [searchParams, setSearchParams] = useSearchParams();

    const [value, refresh] = useRefresh();

    const [result, setResult] = useState(initialResult);
    const [testingState, setTestingState] = useState({
        tests: tests.map(mapToTestState),
        currentTest: 0
    });

    useEffect(() => {
        setResult(initialResult);
        setTestingState({
            tests: tests.map(mapToTestState),
            currentTest: 0
        });
    }, [value]);

    const getMessagePopup = setPopupState => (
        setPopupState({
            bodyGetter: () => <MessagePopupBody message="Test message" />
        })
    );

    const selectVariant = variantId => {
        testingState.tests[testingState.currentTest].userAnswer = variantId;
        setTestingState({
            ...testingState,
        });
    }

    const sendUserAnswer = async testIndex => {
        const test = testingState.tests[testIndex];
        const data = {
            examId: searchParams.get("examId"),
            specializationId: searchParams.get("specializationId"),
            testId: test.id,
            variantId: test.userAnswer
        };

        await updateAnswer(data);
    }

    const previousTest = () => {
        showTest(testingState.currentTest - 1);
    }

    const nextTest = () => {
        showTest(testingState.currentTest + 1);
    }

    const completeTest = async setPopupState => {
        setResult(previous => ({
            ...previous,
            status: loadingStatus.loading
        }));
        setPopupState();
        await sendUserAnswer(testingState.currentTest);
        const result = await getResult();
        setResult(previous => ({
            ...previous,
            status: loadingStatus.completed,
            data: result
        }));
        setResult(result);
    }

    const canMovePrevious = () => {
        return testingState.currentTest > 0;
    }

    const canMoveNext = () => {
        return testingState.currentTest + 1 < testingState.tests.length;
    }

    const canFinish = () => {
        return testingState.currentTest + 1 === testingState.tests.length;
    }

    const startNew = () => {
        setSearchParams({});
    }

    const repeat = () => {
        refresh();
    }

    const getNavigationItems = () => {
        return testingState.tests.map((item, index) => mapExaminationToNavigationItem(item, index === testingState.currentTest));
    }

    const getAnswer = test => ({
        examId: searchParams.get("examId"),
        specializationId: searchParams.get("specializationId"),
        testId: test.id,
        variantId: test.userAnswer
    });

    const showTest = async index => {
        const currentTestIndex = testingState.currentTest;
        const currentTest = testingState.tests[currentTestIndex];
        const answer = getAnswer(currentTest);
        const answerIsChanged = currentTest.lastProcessed !== currentTest.userAnswer;
        setTestingState(previous => {
            previous.tests[currentTestIndex].status = answerIsChanged ? "pending" : previous.tests[currentTestIndex].status;
            return {
                ...previous,
                currentTest: index
            };
        });
        if (!answerIsChanged) {
            return;
        }
        const result = await updateAnswer(answer);
        if (result.error) {
            setTestingState(previous => {
                previous.tests[currentTestIndex].status = "failed";
                return {
                    ...previous,
                };
            })
        } else {
            setTestingState(previous => {
                previous.tests[currentTestIndex].status = "processed";
                previous.tests[currentTestIndex].lastProcessed = result.variantId;
                return {
                    ...previous
                };
            })
        }
    }

    const askToFinish = setPopupState => {
        setPopupState({
            bodyGetter: () => <TestCompletionWarning onClick={() => completeTest(setPopupState)} />
        });
    }

    return (
        <div className="ExaminationTesting full-width s-vflex">
            {
                result.status !== loadingStatus.inProgress ? (
                    <LoaderBoundary condition={result.status === loadingStatus.loading} className="full-width s-hflex-center">
                        <TestingStatistic statistic={{correctCount: result.correctCount, total: result.total}} />
                        <Spacer height={50} />
                        <TestingControl onNew={startNew} onRepeat={repeat} />
                        <Spacer height={100} />
                        <InfoHeader text="Ваш результат" />
                        <TestingCorrectness tests={result.tests} />
                    </LoaderBoundary>
                ) : (
                    <>
                        {
                            testingState.tests.length > 0 ? (
                                <>
                                    <div className="s-hflex-center">
                                        <PopupConsumer>
                                            {
                                                ({setPopupState}) => (
                                                    <Timer duration={duration*60} onExpire={() => getMessagePopup(setPopupState)} />
                                                )
                                            }
                                        </PopupConsumer>
                                    </div>
                                    <div className="question">
                                        <TestingNavigation items={getNavigationItems()} onSelect={showTest} />
                                        <ExaminationTest test={testingState.tests[testingState.currentTest]} onSelect={selectVariant} onRetry={() => showTest(testingState.currentTest)} />
                                    </div>
                                </>
                            ) : (
                                <InfoText className="s-hflex-center">
                                    Немає тестів по Вашому запиту
                                </InfoText>
                            )
                        }
                        <DisplayBoundary condition={canMoveNext() || canMovePrevious() || canFinish()}>
                            <div className="navigation-options s-hflex">
                                <DisplayBoundary condition={canMovePrevious()}>
                                    <Button isFilled={true} onClick={previousTest}>
                                        Попереднє
                                    </Button>
                                </DisplayBoundary>
                                <div className="equal-flex" />
                                <DisplayBoundary condition={canMoveNext()}>
                                    <Button isFilled={true} onClick={nextTest}>
                                        Наступне
                                    </Button>
                                </DisplayBoundary>
                                <DisplayBoundary condition={canFinish()}>
                                    <PopupConsumer>
                                        {
                                            ({setPopupState}) => (
                                                <Button isFilled={true} onClick={() => askToFinish(setPopupState)}>
                                                    Завершити
                                                </Button>
                                            )
                                        }
                                    </PopupConsumer>
                                </DisplayBoundary>
                            </div>
                        </DisplayBoundary>
                    </>
                )
            }
        </div>
    )
}

export default ExaminationTesting;
