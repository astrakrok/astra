import {useEffect, useState} from "react";
import Button from "../Button/Button";
import DisplayBoundary from "../DisplayBoundary/DisplayBoundary";
import RadioButton from "../RadioButton/RadioButton";
import "./TrainingTest.css";

const TrainingTest = ({
    onAnswer = () => {},
    checked = true,
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
            isCorrect: testState.test.variants.find(item => item.id === selected).isCorrect
        });
    }

    const trySelectVariant = variantId => {
        if (testState.userAnswer != null) {
            return;
        }
        setSelected(variantId);
    }

    const renderVariant = variant => {
        const selectStatus = selected === variant.id ? "selected" : "skipped";
        const correctStatus = variant.isCorrect ? "correct" : "incorrect";
        const checked = testState.userAnswer == null ? "" : "checked";

        return (
            <div key={variant.id} className="s-vflex variant">
                <RadioButton
                    className={`radio ${selectStatus} ${correctStatus} ${checked}`}
                    onChange={() => trySelectVariant(variant.id)}
                    contentClassName="title"
                    name={`variant${testState.test.id}`}
                    disabled={testState.userAnswer != null}
                    checked={selected === variant.id}
                >
                    {variant.title}
                </RadioButton>
                {
                    (testState.userAnswer != null) ? (
                        <div className="explanation">
                            {variant.explanation}
                        </div>
                    ) : null
                }
            </div>
        );
    }

    return (
        <div className="TrainingTest">
            <div className="question">
                {order == null ? "" : order + "."} {testState.test.question}
            </div>
            {
                (testState.userAnswer != null) ? (
                    <div className="comment">
                        {testState.test.comment}
                    </div>
                ) : null
            }
            <div className="s-vflex variants">
                {
                    testState.test.variants.map(renderVariant)
                }
                <DisplayBoundary condition={checked}>
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
