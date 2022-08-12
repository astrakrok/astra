import {useEffect, useState} from "react";
import Button from "../Button/Button";
import TrainingTest from "../TrainingTest/TrainingTest";
import TestingStatistic from "../TestingStatistic/TestingStatistic";
import "./TrainingTesting.css";
import {useSearchParams} from "react-router-dom";
import Spacer from "../Spacer/Spacer";
import useRefresh from "../../hook/useRefresh";
import InfoText from "../InfoText/InfoText";
import TestingControl from "../TestingControl/TestingControl";
import TestingNavigation from "../TestingNavigation/TestingNavigation";
import {mapTrainingToNavigationItem} from "../../mapper/test.mapper";
import TestingCorrectness from "../TestingCorrectness/TestingCorrectness";
import InfoHeader from "../InfoHeader/InfoHeader";
import {scrollToTop} from "../../handler/scroll.handler";

const initialStatus = {
    correctCount: 0,
    state: "incomplete"
};

const initialCurrentTest = 0;

const initialTestingState = tests => {
    return tests.map(test => ({
        ...test,
        userAnswer: null,
    }));
};

const TrainingTesting = ({tests}) => {
    const [, setSearchParams] = useSearchParams();

    const [value, refresh] = useRefresh();
    const [status, setStatus] = useState(initialStatus);
    const [testingState, setTestingState] = useState(initialTestingState(tests));
    const [currentTest, setCurrentTest] = useState(initialCurrentTest);

    useEffect(() => {
        setCurrentTest(initialCurrentTest);
        setTestingState(initialTestingState(tests));
        setStatus(initialStatus);
    }, [value]);

    useEffect(() => {
        setTestingState(initialTestingState(tests));
    }, [tests]);

    const displayPreviousButton = () => {
        return currentTest > 0;
    }

    const displayNextButton = () => {
        return currentTest < testingState.length;
    }

    const refreshUserAnswers = (answer, testIndex) => {
        setTestingState(previousTestingState => {
            const test = previousTestingState[testIndex];
            previousTestingState[testIndex] = {
                ...test,
                userAnswer: answer.value
            };
            return [...previousTestingState];
        });
        setStatus(previousStatus => ({
            ...previousStatus,
            correctCount: previousStatus.correctCount + answer.isCorrect
        }));
    }

    const showPreviousTest = () => {
        setCurrentTest(previousCurrentTest => previousCurrentTest - 1);
    }

    const showNextTest = () => {
        if (currentTest + 1 < testingState.length) {
            setCurrentTest(previousCurrentTest => previousCurrentTest + 1);
        } else {
            scrollToTop();
            setStatus(previousStatus => ({
                ...previousStatus,
                stage: "completed"
            }));
        }
    }

    const getTestingStatistic = () => {
        return {
            correctCount: status.correctCount,
            total: testingState.length
        };
    }

    const startNew = () => {
        setSearchParams({});
    }

    const restartTesting = () => {
        refresh();
    }

    const getNavigationItems = () => {
        return testingState.map((test, index) => mapTrainingToNavigationItem(test, index === currentTest))
    }

    const navigateToTest = index => {
        setCurrentTest(index);
    }

    return (
        <div className="TrainingTesting full-width">
            {
                (status.stage === "completed") ? (
                    <div className="s-vflex">
                        <TestingStatistic statistic={getTestingStatistic()} />
                        <Spacer height={50} />
                        <TestingControl onNew={startNew} onRepeat={restartTesting} />
                        <Spacer height={100} />
                        <InfoHeader text="Ваш результат" />
                        <TestingCorrectness tests={testingState} />
                    </div>
                ) : (
                    <>
                        {
                            testingState.length === 0 ? (
                                <InfoText className="s-hflex-center">
                                    Немає тестів по Вашому запиту
                                </InfoText>
                            ) : (
                                <>
                                    <TestingNavigation items={getNavigationItems()} onSelect={navigateToTest} />
                                    <TrainingTest testState={testingState[currentTest]} onAnswer={value => refreshUserAnswers(value, currentTest)} />
                                </>
                            )
                        }
                        <div className="equal-flex" />
                        {
                            (displayPreviousButton() || displayNextButton()) ? (
                                <div className="navigation-options s-hflex">
                                    {
                                        displayPreviousButton() ? (
                                            <Button isFilled={true} onClick={showPreviousTest}>
                                                Попереднє
                                            </Button>
                                        ) : null
                                    }
                                    <div className="equal-flex" />
                                    {
                                        displayNextButton() ? (
                                            <Button isFilled={true} onClick={showNextTest}>
                                                {currentTest + 1 === testingState.length ? "Завершити" : "Наступне"}
                                            </Button>
                                        ) : null
                                    }
                                </div>
                            ) : null
                        }
                    </>
                )
            }
        </div>
    );
}

export default TrainingTesting;
