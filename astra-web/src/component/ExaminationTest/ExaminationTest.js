import Alert from "../Alert/Alert";
import DisplayBoundary from "../DisplayBoundary/DisplayBoundary";
import RadioButton from "../RadioButton/RadioButton";
import Preloader from "../Preloader/Preloader";
import "./ExaminationTest.css";

const ExaminationTest = ({
    test,
    onRetry = () => {},
    onSelect = () => {}
}) => {
    const renderVariant = variant => (
        <div className="variant" key={variant.id}>
            <RadioButton
                onChange={() => onSelect(variant.id)}
                contentClassName="title"
                name={`variant${test.id}`}
                checked={variant.id === test.userAnswer}
            >
                {variant.title}
            </RadioButton>
        </div>
    );

    const checkStatusForAlert = () => ["pending", "processed", "failed"].includes(test.status);
    const renderLastAnswer = test => {
        if (test.lastProcessed === null) {
            return null;
        }
        return "Остання зарахована відповідь була - " + (test.variants
            .map((item, index) => ({
                value: item.id,
                index}))
            .find(item => item.value === test.lastProcessed).index + 1);
    }

    return (
        <div className="ExaminationTest">
            <DisplayBoundary condition={checkStatusForAlert()}>
                <div className="alert">
                    {
                        {
                            "pending": (
                                <Alert type="warning">
                                    <div className="s-hflex">
                                        <div className="s-vflex-center">Ваша відповідь обробляється</div>
                                        <div className="equal-flex" />
                                        <Preloader size="small" />
                                    </div>
                                </Alert>
                            ),
                            "processed": (
                                <Alert type="success">
                                    Ваша відповідь була успішно оброблена та зарахована
                                </Alert>
                            ),
                            "failed": (
                                <Alert type="danger">
                                    На жаль, Вашу відповідь не вдалось зарахувати.
                                    Причиною може бути погане з'єднання з інтернетом або вичерпаний ліміт часу
                                    {renderLastAnswer(test)}
                                    <div className="link" onClick={onRetry}>Спробувати ще раз</div>
                                </Alert>
                            )
                        }[test.status]
                    }
                </div>
            </DisplayBoundary>
            <div className="question">
                {test.question}
            </div>
            <div className="variants s-vflex">
                {
                    test.variants.map(renderVariant)
                }
            </div>
        </div>
    );
}

export default ExaminationTest;
