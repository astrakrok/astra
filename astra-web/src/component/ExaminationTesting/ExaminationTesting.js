import PopupConsumer from "../../context/popup/PopupConsumer";
import Timer from "../Timer/Timer";
import MessagePopupBody from "../popup-component/MessagePopupBody/MessagePopupBody";
import "./ExaminationTesting.css";
import {useEffect, useState} from "react";
import ExaminationTest from "../ExaminationTest/ExaminationTest";
import Button from "../Button/Button";
import DisplayBoundary from "../DisplayBoundary/DisplayBoundary";
import {getResult} from "../../service/examination.service";
import TestingStatistic from "../TestingStatistic/TestingStatistic";
import TestingCorrectness from "../TestingCorrectness/TestingCorrectness";
import Spacer from "../Spacer/Spacer";
import InfoHeader from "../InfoHeader/InfoHeader";
import TestingControl from "../TestingControl/TestingControl";
import {useSearchParams} from "react-router-dom";
import useRefresh from "../../hook/useRefresh";

const ExaminationTesting = ({
    tests,
    duration
}) => {
    const [, setSearchParams] = useSearchParams();

    const [value, refresh] = useRefresh();

    const [result, setResult] = useState(null);
    const [testingState, setTestingState] = useState({
        tests: tests.map(test => ({
            ...test,
            userAnswer: null
        })),
        currentTest: 0
    });

    useEffect(() => {
        setResult(null);
        setTestingState({
            tests: tests.map(test => ({
                ...test,
                userAnswer: null
            })),
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

    const sendUserAnswer = async test => {
        const data = {
            testId: test.id,
            answer: test.userAnswer
        };

        // TODO examinationService.updateAnswer(data)
    }

    const previousTest = () => {
        setTestingState(previous => ({
            ...previous,
            currentTest: previous.currentTest - 1
        }));
    }

    const nextTest = async () => {
        await sendUserAnswer(testingState.tests[testingState.currentTest]);
        setTestingState(previous => ({
            ...previous,
            currentTest: previous.currentTest + 1
        }));
    }

    const finishTest = async () => {
        await sendUserAnswer(testingState.tests[testingState.currentTest]);
        const result = await getResult();
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

    return (
        <div className="ExaminationTesting full-width s-vflex">
            {
                result != null ? (
                    <>
                        <TestingStatistic statistic={{correctCount: result.correctCount, total: result.total}} />
                        <Spacer height={50} />
                        <TestingControl onNew={startNew} onRepeat={repeat} />
                        <Spacer height={100} />
                        <InfoHeader text="Ваш результат" />
                        <TestingCorrectness tests={result.tests} />
                    </>
                ) : (
                    <>
                        <div className="s-hflex header-info">
                            <div className="question-counter s-vflex-end">
                                Питання {testingState.currentTest + 1}/{testingState.tests.length}:
                            </div>
                            <div className="equal-flex" />
                            <PopupConsumer>
                                {
                                    ({setPopupState}) => (
                                        <Timer duration={duration*60} onExpire={() => getMessagePopup(setPopupState)} />
                                    )
                                }
                            </PopupConsumer>
                        </div>
                        <div className="question">
                            <ExaminationTest test={testingState.tests[testingState.currentTest]} onSelect={selectVariant} />
                        </div>
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
                                    <Button isFilled={true} onClick={finishTest}>
                                        Завершити
                                    </Button>
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
