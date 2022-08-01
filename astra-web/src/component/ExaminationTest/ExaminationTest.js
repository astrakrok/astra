import RadioButton from "../RadioButton/RadioButton";
import "./ExaminationTest.css";

const ExaminationTest = ({
    test,
    onSelect = () => {}
}) => {
    const renderVariant = variant => (
        <div className="variant" key={variant.id}>
            <RadioButton
                onChange={() => onSelect(variant.id)}
                contentClassName="title"
                name={`variant${test.id}`}
            >
                {variant.title}
            </RadioButton>
        </div>
    );

    return (
        <div className="ExaminationTest">
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
