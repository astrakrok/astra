import {useEffect, useState} from "react";
import Button from "../Button/Button";
import DisplayBoundary from "../DisplayBoundary/DisplayBoundary";
import RadioButton from "../RadioButton/RadioButton";
import Tooltipped from "../Tooltipped/Tooltipped";
import PopupConsumer from "../../context/popup/PopupConsumer";
import parse from "html-react-parser";
import "./TrainingTest.css";
import ErrorForm from "../form/ErrorForm/ErrorForm";
import Spacer from "../Spacer/Spacer";
import Divider from "../Divider/Divider";
import Badge from "../Badge/Badge";
import Alert from "../Alert/Alert";

const isCorrectSelected = (testState, selected) => {
    const selectedVariant = testState.variants.find(item => item.id === selected);
    if (!selectedVariant) {
        return false;
    }
    return selectedVariant.isCorrect;
}

const TrainingTest = ({
                          onAnswer = () => {
                          },
                          answered = false,
                          order = null,
                          testState
                      }) => {
    const [selected, setSelected] = useState(testState.userAnswer);

    useEffect(() => {
        setSelected(testState.userAnswer);
    }, [testState.userAnswer])

    const check = () => {
        onAnswer({
            value: selected,
            isCorrect: testState.variants.find(item => item.id === selected).isCorrect
        });
    }

    const selectable = () => {
        return testState.userAnswer == null && !answered;
    }

    const trySelectVariant = variantId => {
        if (!selectable()) {
            return;
        }
        setSelected(variantId);
    }

    const openErrorForm = setPopupState => {
        setPopupState({
            bodyGetter: () => <ErrorForm initialValue={testState.question}/>
        });
    }

    const renderVariant = variant => {
        const selectStatus = selected === variant.id ? "selected" : "skipped";
        const correctStatus = variant.isCorrect ? "correct" : "incorrect";
        const checked = selectable() ? "" : "checked";

        return (
            <div key={variant.id} className="s-vflex variant">
                <RadioButton
                    className={`radio ${selectStatus} ${correctStatus} ${checked}`}
                    onChange={() => trySelectVariant(variant.id)}
                    contentClassName="title"
                    name={`variant${testState.id}`}
                    disabled={!selectable()}
                    checked={selected === variant.id}
                >
                    <div className="s-vflex">
                        <span className="line-break">{variant.title}</span>
                        <Spacer height={20}/>
                        {parse(variant.titleSvg || "")}
                    </div>
                </RadioButton>
                {
                    !selectable() ? (

                        <div className="explanation line-break">
                            <div className="s-vflex">
                                {parse(variant.explanation)}
                                {
                                    variant.explanationSvg ? (
                                        <>
                                            <Spacer height={20}/>
                                            {parse(variant.explanationSvg || "")}
                                        </>
                                    ) : null
                                }
                            </div>
                        </div>
                    ) : null
                }
            </div>
        );
    }

    return (
        <div className="TrainingTest" id={`test${testState.id}`}>
            <DisplayBoundary condition={testState.subjects || testState.testings}>
                <DisplayBoundary condition={testState.subjects}>
                    <div className="s-hflex">
                        <span className="weight-strong s-vflex-center">Предмети:</span>
                        <Spacer width={5} />
                        {
                            testState.subjects && testState.subjects.map(item => (
                                <Badge key={item.id} type="light-primary">{item.title}</Badge>
                            ))
                        }
                    </div>
                </DisplayBoundary>
                <Spacer height={10} />
                <DisplayBoundary condition={testState.testings}>
                    <div className="s-hflex">
                        <span className="weight-strong s-vflex-center">Зустрічався у іспитах:</span>
                        <Spacer width={5} />
                        {
                            testState.testings && testState.testings.map(item => (
                                <Badge key={item.id} type="light-secondary">{item.specialization.step.title} | {item.specialization.title} | {item.exam.title}</Badge>
                            ))
                        }
                    </div>
                </DisplayBoundary>
                <Spacer height={10} />
                <Divider />
            </DisplayBoundary>
            <div className="question line-break s-hflex">
                <div className="s-vflex">
                    <span className="text">
                        {order == null ? "" : order + "."} {testState.question}
                    </span>
                    <Spacer height={10}/>
                    <div className="question-svg">
                        {parse(testState.questionSvg || "")}
                    </div>
                </div>
                <span className="equal-flex"/>
                <div>
                    <PopupConsumer>
                        {
                            ({setPopupState}) => (
                                <Tooltipped tooltip="Помилка" position="left">
                                    <span className="error-form-trigger clickable"
                                          onClick={() => openErrorForm(setPopupState)}>
                                        <i className="material-icons small">priority_high</i>
                                    </span>
                                </Tooltipped>
                            )
                        }
                    </PopupConsumer>
                </div>
            </div>
            {
                !selectable() ? (
                    <div className="comment line-break">
                        {parse(testState.comment)}
                        {parse(testState.commentSvg || "")}
                    </div>
                ) : null
            }
            <DisplayBoundary condition={!selectable()}>
                {
                    isCorrectSelected(testState, selected) ? (
                        <Alert type="success">Відповідь правильна!</Alert>
                    ) : (
                        <Alert type="danger">Відповідь неправильна!</Alert>
                    )
                }
            </DisplayBoundary>
            <div className="s-vflex variants">
                {
                    testState.variants.map(renderVariant)
                }
                <DisplayBoundary condition={!answered}>
                    <div className="s-hflex-end" style={{marginTop: 20}}>
                        <Button onClick={check} disabled={testState.userAnswer != null || selected == null}>
                            Перевірити
                        </Button>
                    </div>
                </DisplayBoundary>
            </div>
        </div>
    );
}

export default TrainingTest;
