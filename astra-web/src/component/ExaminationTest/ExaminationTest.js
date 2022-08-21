import Alert from "../Alert/Alert";
import DisplayBoundary from "../DisplayBoundary/DisplayBoundary";
import RadioButton from "../RadioButton/RadioButton";
import Preloader from "../Preloader/Preloader";
import "./ExaminationTest.css";
import ErrorForm from "../form/ErrorForm/ErrorForm";
import PopupConsumer from "../../context/popup/PopupConsumer";
import Tooltipped from "../Tooltipped/Tooltipped";
import parse from "html-react-parser";
import Spacer from "../Spacer/Spacer";

const ExaminationTest = ({
                             test,
                             order = null,
                             onRetry = () => {
                             },
                             onSelect = () => {
                             }
                         }) => {
    const renderVariant = variant => (
        <div className="variant" key={variant.id}>
            <RadioButton
                onChange={() => onSelect(variant.id)}
                contentClassName="title"
                name={`variant${test.id}`}
                checked={variant.id === test.userAnswer}
            >
                <div className="s-vflex">
                    <span className="line-break">{variant.title}</span>
                    <Spacer height={20}/>
                    {parse(variant.titleSvg || "")}
                </div>
            </RadioButton>
        </div>
    );

    const checkStatusForAlert = () => ["pending", "processed", "failed"].includes(test.status);
    
    const renderLastAnswer = test => {
        if (test.lastProcessed == null) {
            return null;
        }
        return "Остання зарахована відповідь була - " + (test.variants
            .map((item, index) => ({
                value: item.id,
                index
            }))
            .find(item => item.value === test.lastProcessed).index + 1);
    }

    const openErrorForm = setPopupState => {
        setPopupState({
            bodyGetter: () => <ErrorForm initialValue={test.question}/>
        });
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
            <div className="question s-hflex">
                <div className="s-vflex">
                    <span className="text">
                        {order == null ? "" : order + "."} {test.question}
                    </span>
                    <Spacer height={10}/>
                    <div className="question-svg">
                        {parse(test.questionSvg || "")}
                    </div>
                </div>
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
            <div className="variants s-vflex">
                {
                    test.variants.map(renderVariant)
                }
            </div>
        </div>
    );
}

export default ExaminationTest;
