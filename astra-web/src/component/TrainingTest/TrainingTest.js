import {useEffect, useState} from "react";
import Button from "../Button/Button";
import DisplayBoundary from "../DisplayBoundary/DisplayBoundary";
import RadioButton from "../RadioButton/RadioButton";
import Tooltipped from "../Tooltipped/Tooltipped";
import PopupConsumer from "../../context/popup/PopupConsumer";
import parse from "html-react-parser";
import "./TrainingTest.css";
import ErrorForm from "../form/ErrorForm/ErrorForm";

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
            bodyGetter: () => <ErrorForm/>
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
                    <span className="line-break">{variant.title}</span>
                </RadioButton>
                {
                    !selectable() ? (
                        <div className="explanation line-break">
                            {parse(variant.explanation)}
                        </div>
                    ) : null
                }
            </div>
        );
    }

    return (
        <div className="TrainingTest" id={`test${testState.id}`}>
            <div className="question line-break s-hflex">
                <span className="text">
                    {order == null ? "" : order + "."} {testState.question}
                </span>
                <span className="equal-flex"/>
                <div>
                    <PopupConsumer>
                        {
                            ({setPopupState}) => (
                                <Tooltipped tooltip="Помилка" position="top">
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
                    </div>
                ) : null
            }
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
